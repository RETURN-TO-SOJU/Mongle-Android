package com.won983212.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.view.daydetail.model.AnalyzedEmotion
import com.won983212.mongle.presentation.view.daydetail.model.Photo
import com.won983212.mongle.presentation.view.daydetail.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DayDetailViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
) : BaseViewModel() {

    lateinit var date: LocalDate
        private set

    var emotion: Emotion? = null
        private set

    private val _eventOpenGiftDialog = SingleLiveEvent<LocalDate>()
    val eventOpenGiftDialog = _eventOpenGiftDialog.asLiveData()

    private val _analyzedEmotions = MutableLiveData(listOf<AnalyzedEmotion>())
    val analyzedEmotions = _analyzedEmotions.asLiveData()

    private val _photos = MutableLiveData(listOf<Photo>())
    val photos = _photos.asLiveData()

    private val _schedules = MutableLiveData(listOf<Schedule>())
    val schedules = _schedules.asLiveData()

    private val _emotionIcon = MutableLiveData(R.drawable.ic_add_emotion)
    val emotionIcon = _emotionIcon.asLiveData()

    private val _diary = MutableLiveData("")
    val diary = _diary.asLiveData()
    val diaryText = Transformations.map(_diary) {
        if (it.isBlank()) {
            TextResource(R.string.overview_title_empty)
        } else {
            TextResource(it)
        }
    }

    // TODO model mapper를 따로 제작하면 좋겠다
    fun initializeFromIntent(intent: Intent) {
        date = (intent.getSerializableExtra(DayDetailActivity.EXTRA_DATE)
            ?: LocalDate.now()) as LocalDate

        val giftDate =
            intent.getBooleanExtra(DayDetailActivity.EXTRA_SHOW_ARRIVED_GIFT_DIALOG, false)
        if (giftDate) {
            _eventOpenGiftDialog.value = date
        }

        viewModelScope.launch {
            val detail = calendarRepository.getCalendarDayDetail(this@DayDetailViewModel, date)
            if (detail != null) {
                emotion = detail.emotion
                _diary.postValue(detail.diary)
                if (detail.emotion != null) {
                    _emotionIcon.postValue(detail.emotion.iconRes)
                }
                _schedules.postValue(detail.scheduleList.map {
                    val formatter = DateTimeFormatter.ofPattern("a hh:mm")
                    val timeRangeText =
                        "${it.startTime.format(formatter)} ~ ${it.endTime.format(formatter)}"
                    Schedule(it.name, it.calendar, timeRangeText)
                })
                _photos.postValue(detail.imageList.map {
                    val formatter = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.US)
                    Photo(it.url, it.time.format(formatter))
                })
                _analyzedEmotions.postValue(detail.emotionList.map {
                    AnalyzedEmotion(it.emotion, it.percent)
                })
            }
        }
    }
}