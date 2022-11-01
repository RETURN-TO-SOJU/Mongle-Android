package com.rtsoju.mongle.presentation.view.diary

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.R
import com.rtsoju.mongle.domain.usecase.calendar.UpdateDiaryUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.SingleLiveEvent
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.presentation.util.getSerializableExtraCompat
import com.rtsoju.mongle.util.DatetimeFormats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditDiaryViewModel @Inject constructor(
    private val updateDiary: UpdateDiaryUseCase
) : BaseViewModel() {

    private val _emotionRes = MutableLiveData(R.drawable.ic_edit)
    val emotionRes = _emotionRes.asLiveData()

    private val _diaryDate = MutableLiveData<LocalDate>()
    val diaryDate = Transformations.map(_diaryDate) {
        it.format(DatetimeFormats.DATE_KR)
    }

    val diaryMaxLength = MutableLiveData(1000).asLiveData()
    val diaryContent = MutableLiveData("")
    val diaryLimitText = Transformations.map(diaryContent) {
        "%d / %d자".format(it.length, diaryMaxLength.value ?: 0)
    }

    private val _eventUpdateComplete = SingleLiveEvent<Unit>()
    val eventUpdateComplete = _eventUpdateComplete.asLiveData()

    fun initializeByIntent(intent: Intent) {
        _emotionRes.value = intent.getIntExtra(
            EditDiaryActivity.EXTRA_EMOTION_RES,
            R.drawable.ic_edit
        )
        _diaryDate.value = intent.getSerializableExtraCompat(
            EditDiaryActivity.EXTRA_DATE,
            LocalDate.now()
        )
        diaryContent.value = intent.getStringExtra(EditDiaryActivity.EXTRA_INITIAL_DIARY) ?: ""
    }

    fun commitDiary() = viewModelScope.launch(Dispatchers.IO) {
        val date = _diaryDate.value
        if (date != null) {
            val result = startProgressTask {
                updateDiary(
                    date, diaryContent.value ?: ""
                )
            }
            if (result != null) {
                _eventUpdateComplete.post()
            }
        }
    }
}