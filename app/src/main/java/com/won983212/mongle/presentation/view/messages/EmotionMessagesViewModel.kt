package com.won983212.mongle.presentation.view.messages

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.util.asLiveData
import com.won983212.mongle.presentation.util.getSerializableExtraCompat
import com.won983212.mongle.util.DatetimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EmotionMessagesViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val passwordRepository: PasswordRepository
) : BaseViewModel() {

    private var isUnlocked = false

    private val _messages = MutableLiveData(listOf<EmotionMessage>())
    val messages = _messages.asLiveData()

    private val selectedEmotion = MutableLiveData(Emotion.values()[0])

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
                calendarRepository.getDayEmotionalSentences(
                    date,
                    selected
                )
            }
            if (messages != null) {
                val mappedMessages = messages.map {
                    EmotionMessage(it.emotion, it.sentence)
                }
                _messages.postValue(mappedMessages)
                if (useAutoUnlock) {
                    doUnlock(mappedMessages)
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
            _messages.postValue(messages.map {
                EmotionMessage(it.emotion, passwordRepository.decryptByKeyPassword(it.message))
            })
        }
    }

    fun unlock() {
        useAutoUnlock = true
        if (_messages.value?.isEmpty() == false) {
            doUnlock(_messages.value!!)
        }
    }

    fun needShowUnlockDialog() = passwordRepository.hasDataKeyPassword() && !useAutoUnlock

    companion object {
        const val TAG = "EmotionMessagesViewModel"
        private var useAutoUnlock = false
    }
}