package com.rtsoju.mongle.presentation.view.messages

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.R
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.usecase.calendar.GetDayEmotionalSentencesUseCase
import com.rtsoju.mongle.domain.usecase.password.DecryptByKeyPasswordUseCase
import com.rtsoju.mongle.domain.usecase.password.HasDataKeyPasswordUseCase
import com.rtsoju.mongle.exception.CannotDecryptException
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.SingleLiveEvent
import com.rtsoju.mongle.presentation.util.TextResource
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.presentation.util.getSerializableExtraCompat
import com.rtsoju.mongle.util.DatetimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EmotionMessagesViewModel @Inject constructor(
    private val getDayEmotionalSentences: GetDayEmotionalSentencesUseCase,
    private val decryptByKeyPassword: DecryptByKeyPasswordUseCase,
    private val hasDataKeyPassword: HasDataKeyPasswordUseCase
) : BaseViewModel() {

    private var isUnlocked = false

    private val _messages = MutableLiveData(listOf<EmotionMessage>())
    val messages = _messages.asLiveData()
    val hasMessages = Transformations.map(_messages) {
        it != null && it.isNotEmpty()
    }

    private val selectedEmotion = MutableLiveData(Emotion.values()[0])

    private val _eventNeedsUnlock = SingleLiveEvent<Unit>()
    val eventNeedsUnlock = _eventNeedsUnlock.asLiveData()

    private val _date = MutableLiveData(LocalDate.now())
    val date = Transformations.map(_date) {
        it.format(DatetimeFormats.DATE_KR_FULL)
    }

    private val _proportionText =
        MutableLiveData(TextResource(R.string.emotion_message_proportion, "??", 0))
    val proportionText = _proportionText.asLiveData()

    private var emotionProportions: Map<Emotion, Int>? = null

    @Suppress("UNCHECKED_CAST")
    fun initializeByIntent(intent: Intent) {
        selectedEmotion.value = intent.getSerializableExtraCompat(
            EmotionMessagesActivity.EXTRA_EMOTION,
            Emotion.values()[0]
        )
        _date.value = intent.getSerializableExtraCompat(
            EmotionMessagesActivity.EXTRA_DATE,
            LocalDate.now()
        )
        emotionProportions = intent.getSerializableExtraCompat(
            EmotionMessagesActivity.EXTRA_PROPORTIONS
        )

        loadMessages()
    }

    private fun loadMessages() = viewModelScope.launch(Dispatchers.IO) {
        val selected = selectedEmotion.value
        val date = _date.value
        if (selected != null && date != null) {
            updateProportionText()
            val messages = startProgressTask {
                getDayEmotionalSentences(
                    date,
                    selected
                )
            }
            Log.d("EmotionMessagesViewModel", messages?.joinToString() ?: "null")
            if (messages != null) {
                val mappedMessages = messages.map {
                    EmotionMessage(it.emotion, it.sentence, it.roomName)
                }
                _messages.postValue(mappedMessages)

                if (useAutoUnlock) {
                    doUnlock(mappedMessages)
                } else if (mappedMessages.isNotEmpty() && hasDataKeyPassword()) {
                    _eventNeedsUnlock.post()
                }
            }
        } else {
            Log.e(TAG, "Can't update messages. selected or date is null.")
        }
    }

    private fun updateProportionText() {
        val selected = selectedEmotion.value ?: return
        val proportions = emotionProportions
        val proportion = if (proportions == null) {
            0
        } else {
            proportions[selected] ?: 0
        }

        _proportionText.postValue(
            TextResource(
                R.string.emotion_message_proportion,
                TextResource(selected.labelRes),
                proportion
            )
        )
    }

    private fun doUnlock(messages: List<EmotionMessage>) {
        if (!isUnlocked) {
            isUnlocked = true
            val decryptedMessages = try {
                messages.map {
                    EmotionMessage(it.emotion, decryptByKeyPassword(it.message), it.roomName)
                }
            } catch (e: CannotDecryptException) {
                if (e.message != null) {
                    showError(e.message!!)
                }
                messages
            }
            _messages.postValue(decryptedMessages)
        }
    }

    fun unlock() {
        useAutoUnlock = true
        if (_messages.value?.isEmpty() == false) {
            doUnlock(_messages.value!!)
        }
    }

    companion object {
        const val TAG = "EmotionMessagesViewModel"
        private var useAutoUnlock = false
    }
}