package com.won983212.mongle.presentation.view.overview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.usecase.calendar.GetCalendarDayDetailUseCase
import com.won983212.mongle.domain.usecase.calendar.GetCalendarDayMetadataUseCase
import com.won983212.mongle.domain.usecase.user.GetUserInfoUseCase
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.util.asLiveData
import com.won983212.mongle.util.DatetimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val getUserInfo: GetUserInfoUseCase,
    private val getCalendarDayMetadata: GetCalendarDayMetadataUseCase,
    private val getCalendarDayDetail: GetCalendarDayDetailUseCase
) : BaseViewModel() {

    private var keywordMap = mapOf<LocalDate, List<String>>()
    private val _keywords = MutableLiveData<List<String>>(listOf())
    val keywords = Transformations.map(_keywords) { value ->
        value.ifEmpty {
            listOf("비어있음")
        }
    }

    private val calendarEmotions = mutableMapOf<LocalDate, Emotion>()
    private val _eventCalendarDataLoaded = SingleLiveEvent<Map<LocalDate, Emotion>>()
    val eventCalendarDataLoaded = _eventCalendarDataLoaded.asLiveData()

    private val _overviewText = MutableLiveData(TextResource())
    val overviewText = _overviewText.asLiveData()

    private val _selectedDayEmotion = MutableLiveData(R.drawable.emotion_anxious)
    val selectedDayEmotion = _selectedDayEmotion.asLiveData()

    private val _diaryFeedback = MutableLiveData(TextResource(R.string.detail_diary_empty))
    val diaryFeedback = _diaryFeedback.asLiveData()

    val hasData = MutableLiveData(false)


    private fun getOverviewText(date: LocalDate): TextResource {
        if (date == LocalDate.now()) {
            return TextResource(R.string.overview_intro_message_today)
        }

        val emotion = calendarEmotions[date]
        val resId = emotion?.descriptionRes
            ?: R.string.overview_intro_message_other_day
        return TextResource(resId)
    }

    fun loadCalendarData(from: YearMonth, to: YearMonth) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("OverviewViewModel", "LOAD $from ~ $to")
        val days = startProgressTask {
            getCalendarDayMetadata(from, to)
        }
        if (days != null) {
            val emotionData = days.associate { it.date to it.emotion }
            calendarEmotions.putAll(emotionData)
            _eventCalendarDataLoaded.postValue(emotionData)
            keywordMap = days.associate { it.date to it.subjectList }
        }
    }

    fun setSelectedDate(date: LocalDate) = viewModelScope.launch(Dispatchers.IO) {
        setLoading(true)
        val userInfo = startResultTask { getUserInfo() }
        if (userInfo != null) {
            val dateText = date.format(DatetimeFormats.DATE_KR_WEEKDAY)
            _overviewText.postValue(
                TextResource(
                    R.string.overview_intro,
                    userInfo.username,
                    dateText,
                    getOverviewText(date)
                )
            )
        }

        val emotion = calendarEmotions[date]
        _selectedDayEmotion.postValue((emotion ?: Emotion.ANXIOUS).iconRes)

        val defaultFeedback = emotion?.descriptionRes ?: R.string.overview_title_empty
        hasData.postValue(emotion != null)

        val detail = startResultTask { getCalendarDayDetail(date) }
        if (detail != null && detail.diaryFeedback.isNotBlank()) {
            _diaryFeedback.postValue(TextResource(detail.diaryFeedback))
        } else {
            _diaryFeedback.postValue(TextResource(defaultFeedback))
        }

        _keywords.postValue(keywordMap[date] ?: listOf())
        setLoading(false)
    }
}