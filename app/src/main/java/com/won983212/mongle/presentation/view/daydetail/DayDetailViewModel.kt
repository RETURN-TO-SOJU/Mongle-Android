package com.won983212.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.model.Favorite
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.domain.repository.FavoriteRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.view.daydetail.model.AnalyzedEmotion
import com.won983212.mongle.presentation.view.daydetail.model.Photo
import com.won983212.mongle.presentation.view.daydetail.model.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayDetailViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
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

    private val _localPhotos = MutableLiveData(listOf<Photo>())
    private val _photos = MutableLiveData(listOf<Photo>())
    val photos = Transformations.switchMap(_localPhotos) { local ->
        Transformations.map(_photos) {
            if (local == null) {
                it
            } else {
                local + it
            }
        }
    }

    private val _schedules = MutableLiveData(listOf<Schedule>())
    val schedules = _schedules.asLiveData()

    private val _emotionIcon = MutableLiveData(R.drawable.ic_add_emotion)
    val emotionIcon = _emotionIcon.asLiveData()

    private val _diary = MutableLiveData("")
    val diary = _diary.asLiveData()
    val diaryText = Transformations.map(_diary) {
        val text = it ?: ""
        if (text.isBlank()) {
            TextResource(R.string.overview_title_empty)
        } else {
            TextResource(text)
        }
    }

    fun initializeByIntent(intent: Intent) {
        val date = (intent.getSerializableExtra(DayDetailActivity.EXTRA_DATE)
            ?: LocalDate.now()) as LocalDate
        setDate(date)

        val giftDate =
            intent.getBooleanExtra(DayDetailActivity.EXTRA_SHOW_ARRIVED_GIFT_DIALOG, false)
        if (giftDate) {
            _eventOpenGiftDialog.value = date
        }
    }

    fun addFavorite(title: String, emotion: Emotion) = viewModelScope.launch {
        favoriteRepository.insert(Favorite(0, emotion, date, title))
        showMessage("찜 목록에 추가되었습니다.")
    }

    fun setDate(date: LocalDate) {
        this.date = date
        refresh()
    }

    fun setLocalPhoto(photos: List<Photo>) {
        _localPhotos.postValue(photos)
    }

    fun refresh() = viewModelScope.launch {
        val detail = startProgressTask { calendarRepository.getCalendarDayDetail(date) }
        if (detail != null) {
            emotion = detail.emotion
            _diary.postValue(detail.diary)
            detail.emotion?.let {
                _emotionIcon.postValue(it.iconRes)
            }
            _schedules.postValue(detail.scheduleList.map { Schedule.fromResponse(it) })
            _photos.postValue(detail.imageList.map { Photo.fromResponse(it) })
            _analyzedEmotions.postValue(detail.emotionList.map { AnalyzedEmotion.fromResponse(it) })
        }
    }
}