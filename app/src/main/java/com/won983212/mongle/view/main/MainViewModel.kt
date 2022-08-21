package com.won983212.mongle.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.won983212.mongle.common.Emotion
import com.won983212.mongle.common.util.asLiveData
import java.time.LocalDate

class MainViewModel : ViewModel() {
    private val _keywords = MutableLiveData<List<Keyword>>(listOf())
    val keywords = Transformations.map(_keywords) { value ->
        value.ifEmpty {
            listOf(Keyword("비어있음"))
        }
    }

    private val _hasData = MutableLiveData(false)
    val hasData = _hasData.asLiveData()

    private val _calendarEmotions = MutableLiveData(mapOf<LocalDate, Emotion>())
    val calendarEmotions = _calendarEmotions.asLiveData()

    // TODO 텍스트는 어떻게 넣어야 할까...
    private val _overviewText = MutableLiveData("안녕?\n너가 소마니?")
    val overviewText = _overviewText.asLiveData()
}