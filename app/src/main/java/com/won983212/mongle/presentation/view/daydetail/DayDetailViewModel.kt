package com.won983212.mongle.presentation.view.daydetail

import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.DatetimeFormats
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
import java.time.LocalDateTime
import java.time.ZoneOffset
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

    fun setDate(date: LocalDate) {
        this.date = date
        refresh()
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

    fun readPhotosFromCursor(cursor: Cursor) {
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val addedColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
        val photoList = mutableListOf<Photo>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val added = cursor.getLong(addedColumn)
            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
            )
            val addedText = LocalDateTime.ofEpochSecond(added, 0, ZoneOffset.UTC)
                .format(DatetimeFormats.TIME_12)
            photoList.add(Photo(contentUri.toString(), addedText))
        }
        _localPhotos.postValue(photoList)
    }
}