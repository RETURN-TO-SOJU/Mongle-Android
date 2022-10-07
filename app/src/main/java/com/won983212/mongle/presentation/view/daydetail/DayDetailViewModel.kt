package com.won983212.mongle.presentation.view.daydetail

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.R
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.Favorite
import com.won983212.mongle.domain.repository.FavoriteRepository
import com.won983212.mongle.domain.usecase.calendar.GetCalendarDayDetailUseCase
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.util.asLiveData
import com.won983212.mongle.presentation.util.getSerializableExtraCompat
import com.won983212.mongle.presentation.view.daydetail.model.AnalyzedEmotionPresentationModel
import com.won983212.mongle.presentation.view.daydetail.model.PhotoPresentationModel
import com.won983212.mongle.presentation.view.daydetail.model.SchedulePresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayDetailViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
    private val getCalendarDayDetail: GetCalendarDayDetailUseCase
) : BaseViewModel() {

    lateinit var date: LocalDate
        private set

    var emotion: Emotion? = null
        private set

    private val _eventOpenGiftDialog = SingleLiveEvent<LocalDate>()
    val eventOpenGiftDialog = _eventOpenGiftDialog.asLiveData()

    private val _analyzedEmotions = MutableLiveData(listOf<AnalyzedEmotionPresentationModel>())
    val analyzedEmotions = _analyzedEmotions.asLiveData()

    private val _localPhotos = MutableLiveData(listOf<PhotoPresentationModel>())
    private val _photos = MutableLiveData(listOf<PhotoPresentationModel>())
    val photos = Transformations.switchMap(_localPhotos) { local ->
        Transformations.map(_photos) {
            if (local == null) {
                it
            } else {
                local + it
            }
        }
    }

    private val _schedules = MutableLiveData(listOf<SchedulePresentationModel>())
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
        val date: LocalDate = intent.getSerializableExtraCompat(
            DayDetailActivity.EXTRA_DATE,
            LocalDate.now()
        )
        setDate(date)

        val giftDate =
            intent.getBooleanExtra(DayDetailActivity.EXTRA_SHOW_ARRIVED_GIFT_DIALOG, false)
        if (giftDate) {
            _eventOpenGiftDialog.value = date
        }
    }

    fun addFavorite(title: String, emotion: Emotion) = viewModelScope.launch(Dispatchers.IO) {
        favoriteRepository.insert(Favorite(0, emotion, date, title))
        showMessage("찜 목록에 추가되었습니다.")
    }

    fun setDate(date: LocalDate) {
        this.date = date
        refresh()
    }

    fun setLocalPhoto(photos: List<PhotoPresentationModel>) {
        _localPhotos.postValue(photos)
    }

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        val detail = startProgressTask { getCalendarDayDetail(date) }
        if (detail != null) {
            emotion = detail.emotion
            _diary.postValue(detail.diary)
            detail.emotion?.let {
                _emotionIcon.postValue(it.iconRes)
            }
            _schedules.postValue(detail.scheduleList.map {
                SchedulePresentationModel.fromDomainModel(
                    it
                )
            })
            _photos.postValue(detail.imageList.map { PhotoPresentationModel.fromDomainModel(it) })
            _analyzedEmotions.postValue(detail.emotionList.map {
                AnalyzedEmotionPresentationModel.fromDomainModel(
                    it
                )
            })
        }
    }
}