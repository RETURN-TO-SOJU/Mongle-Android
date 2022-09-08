package com.won983212.mongle.presentation.view.diary

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.won983212.mongle.DatetimeFormats
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EditDiaryViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
) : BaseViewModel() {

    private val _emotionRes = MutableLiveData(R.drawable.ic_edit)
    val emotionRes = _emotionRes.asLiveData()

    private val _diaryDate = MutableLiveData<LocalDate>()
    val diaryDate = Transformations.map(_diaryDate) {
        it.format(DatetimeFormats.DATE_KR)
    }

    val diaryContent = MutableLiveData("")

    private val _eventUpdateComplete = SingleLiveEvent<Unit>()
    val eventUpdateComplete = _eventUpdateComplete.asLiveData()

    fun initializeByIntent(intent: Intent) {
        _emotionRes.value = intent.getIntExtra(
            EditDiaryActivity.EXTRA_EMOTION_RES,
            R.drawable.ic_edit
        )
        _diaryDate.value = (intent.getSerializableExtra(EditDiaryActivity.EXTRA_DATE)
            ?: LocalDate.now()) as LocalDate
        diaryContent.value = intent.getStringExtra(EditDiaryActivity.EXTRA_INITIAL_DIARY) ?: ""
    }

    fun commitDiary() = viewModelScope.launch {
        val date = _diaryDate.value
        if (date != null) {
            val result = startProgressTask {
                calendarRepository.updateDiary(
                    date, diaryContent.value ?: ""
                )
            }
            if (result != null) {
                _eventUpdateComplete.call()
            }
        }
    }
}