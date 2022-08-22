package com.won983212.mongle.presentation.view.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.TextResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val calendarRepository: CalendarRepository
) : BaseViewModel() {

    // TODO Emotions랑 통합하자
    private var keywordMap = mapOf<LocalDate, List<String>>()

    private val _keywords = MutableLiveData<List<String>>(listOf())
    val keywords = Transformations.map(_keywords) { value ->
        value.ifEmpty {
            listOf("비어있음")
        }
    }

    private val _calendarEmotions = MutableLiveData(mapOf<LocalDate, Emotion>())
    val calendarEmotions = _calendarEmotions.asLiveData()

    private val _overviewText = MutableLiveData(TextResource())
    val overviewText = _overviewText.asLiveData()

    private val _selectedDayEmotion = MutableLiveData(R.drawable.emotion_anxious)
    val selectedDayEmotion = _selectedDayEmotion.asLiveData()

    private val _selectedDayTitle = MutableLiveData(R.string.detail_diary_empty)
    val selectedDayTitle = _selectedDayTitle.asLiveData()

    val hasData = Transformations.map(_selectedDayTitle) {
        it != R.string.overview_title_empty
    }


    private fun getOverviewText(date: LocalDate): TextResource {
        if (date == LocalDate.now()) {
            return TextResource(R.string.overview_intro_message_today)
        }

        val emotion = calendarEmotions.value?.get(date)
        val resId = emotion?.descriptionRes
            ?: R.string.overview_intro_message_other_day
        return TextResource(resId)
    }

    private suspend fun updateOverviewText(date: LocalDate) {
        val userInfo = userRepository.getUserInfo(this@OverviewViewModel)
        if (userInfo != null) {
            val dateText = date.format(DateTimeFormatter.ofPattern("M월 d일 EEEE"))
            _overviewText.value = TextResource(
                R.string.overview_intro,
                userInfo.name,
                dateText,
                getOverviewText(date)
            )
        }
    }

    fun synchronize() = viewModelScope.launch {
        val today = LocalDate.now()
        val days = calendarRepository.getCalendarDayMetadata(
            this@OverviewViewModel,
            today.minusMonths(3),
            today.plusMonths(3)
        )
        if (days != null) {
            _calendarEmotions.postValue(days.associate { it.date to it.emotion })
            keywordMap = days.associate { it.date to it.subjectList }
        }
    }

    fun onSelectionChanged(date: LocalDate) = viewModelScope.launch {
        updateOverviewText(date)
        val emotion = calendarEmotions.value?.get(date)
        _selectedDayEmotion.postValue((emotion ?: Emotion.ANXIOUS).iconRes)
        _selectedDayTitle.postValue(emotion?.descriptionRes ?: R.string.overview_title_empty)
        _keywords.postValue(keywordMap.get(date) ?: listOf())
    }
}