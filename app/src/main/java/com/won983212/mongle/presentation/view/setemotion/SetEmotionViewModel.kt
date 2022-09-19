package com.won983212.mongle.presentation.view.setemotion

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SetEmotionViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
) : BaseViewModel() {

    private val _selectedEmotion = MutableLiveData(Emotion.values()[0])
    val selectedEmotion = _selectedEmotion.asLiveData()

    private lateinit var date: LocalDate


    fun initializeFromBundle(bundle: Bundle) {
        // TODO getSerializable 등을 localdate, localdatetime 등으로 템플릿 함수 제작
        val initialEmotion =
            bundle.getSerializable(SetEmotionFragment.ARGUMENT_INITIAL_EMOTION) as? Emotion
        date = bundle.getSerializable(SetEmotionFragment.ARGUMENT_DATE) as LocalDate

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
            calendarRepository.updateEmotion(date, it)
        }
    }
}