package com.rtsoju.mongle.presentation.view.overview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.R
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.usecase.calendar.GetCalendarDayDetailUseCase
import com.rtsoju.mongle.domain.usecase.calendar.GetCalendarDayMetadataUseCase
import com.rtsoju.mongle.domain.usecase.user.GetUserInfoUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.SingleLiveEvent
import com.rtsoju.mongle.presentation.util.TextResource
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.util.DatetimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

    private val _diaryFeedback = MutableLiveData(TextResource(R.string.empty))
    val diaryFeedback = _diaryFeedback.asLiveData()

    val hasData = MutableLiveData(false)

    private var apiCallMutex = Mutex()


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
        val days = apiCallMutex.withLock {
            startProgressTask {
                getCalendarDayMetadata(from, to)
            }
        }
        if (days != null) {
            val emotionData = days
                .filter { it.emotion != null }
                .associate { it.date to it.emotion as Emotion }
            calendarEmotions.putAll(emotionData)
            _eventCalendarDataLoaded.postValue(emotionData)
            keywordMap = keywordMap + days.associate { it.date to it.keywords }
        }
    }

    fun updateEmotion(date: LocalDate, emotion: Emotion?) {
        val actualEmotion = emotion ?: Emotion.ANXIOUS
        calendarEmotions[date] = actualEmotion
        _selectedDayEmotion.postValue(actualEmotion.iconRes)
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

        val detail = apiCallMutex.withLock {
            startResultTask { getCalendarDayDetail(date) }
        }
        if (detail != null && detail.diaryFeedback.isNotBlank()) {
            _diaryFeedback.postValue(TextResource(detail.diaryFeedback))
        } else {
            _diaryFeedback.postValue(TextResource(defaultFeedback))
        }

        _keywords.postValue(keywordMap[date] ?: listOf())
        setLoading(false)
    }
}