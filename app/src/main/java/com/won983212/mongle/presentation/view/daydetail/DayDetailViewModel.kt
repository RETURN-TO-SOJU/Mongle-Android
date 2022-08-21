package com.won983212.mongle.presentation.view.daydetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.won983212.mongle.R
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.presentation.base.BaseViewModel
import com.won983212.mongle.presentation.util.TextResource
import com.won983212.mongle.presentation.view.daydetail.model.AnalyzedEmotion
import com.won983212.mongle.presentation.view.daydetail.model.Photo
import com.won983212.mongle.presentation.view.daydetail.model.Schedule

class DayDetailViewModel : BaseViewModel() {
    // TODO (DEBUG) mocking data
    private val _analyzedEmotions = MutableLiveData(
        mutableListOf(
            AnalyzedEmotion(Emotion.ANGRY, 15),
            AnalyzedEmotion(Emotion.ANXIOUS, 20),
            AnalyzedEmotion(Emotion.SAD, 20),
            AnalyzedEmotion(Emotion.HAPPY, 15),
            AnalyzedEmotion(Emotion.TIRED, 10),
            AnalyzedEmotion(Emotion.NEUTRAL, 20)
        )
    )
    val analyzedEmotions = _analyzedEmotions.asLiveData()

    private val _photos = MutableLiveData(
        mutableListOf(
            Photo(null, "02:17 PM"),
            Photo(null, "07:10 PM"),
            Photo(null, "01:00 AM"),
            Photo(null, "04:19 AM")
        )
    )
    val photos = _photos.asLiveData()

    private val _schedules = MutableLiveData(
        mutableListOf(
            Schedule("기획 발표", "네이버 캘린더", "오후 3:00\n~ 오후 5:00"),
            Schedule("중간 발표", "네이버 캘린더", "오후 1:00\n~ 오후 4:00"),
            Schedule("기말 발표", "구글 캘린더", "오후 4:00\n~ 오후 6:00"),
            Schedule("생일", "구글 캘린더", "하루종일"),
            Schedule("에어컨 수리", "구글 캘린더", "오후 3:00\n~ 오후 5:00"),
        )
    )
    val schedules = _schedules.asLiveData()

    private val _emotion = MutableLiveData(R.drawable.ic_add_emotion)
    val emotion = _emotion.asLiveData()

    private val _diary = MutableLiveData(TextResource(""))
    val diary = Transformations.map(_diary) {
        if (it.text.isBlank()) {
            TextResource(R.string.overview_title_empty)
        } else {
            it
        }
    }
}