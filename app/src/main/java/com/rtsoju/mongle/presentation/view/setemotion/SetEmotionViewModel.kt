package com.rtsoju.mongle.presentation.view.setemotion

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.usecase.calendar.UpdateEmotionUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.presentation.util.getSerializableCompat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SetEmotionViewModel @Inject constructor(
    private val updateEmotion: UpdateEmotionUseCase
) : BaseViewModel() {

    private val _selectedEmotion = MutableLiveData(Emotion.values()[0])
    val selectedEmotion = _selectedEmotion.asLiveData()

    private lateinit var date: LocalDate


    fun initializeFromBundle(bundle: Bundle) {
        val initialEmotion: Emotion? =
            bundle.getSerializableCompat(
                SetEmotionFragment.ARGUMENT_INITIAL_EMOTION
            )
        date = bundle.getSerializableCompat(
            SetEmotionFragment.ARGUMENT_DATE,
            LocalDate.now()
        )

        if (initialEmotion != null) {
            selectEmotion(initialEmotion)
        } else {
            selectEmotion(Emotion.values()[0])
        }
    }

    fun selectEmotion(emotion: Emotion) {
        _selectedEmotion.postValue(emotion)
    }

    fun saveEmotion() = viewModelScope.launch(Dispatchers.IO) {
        selectedEmotion.value?.let {
            updateEmotion(date, it)
        }
    }
}