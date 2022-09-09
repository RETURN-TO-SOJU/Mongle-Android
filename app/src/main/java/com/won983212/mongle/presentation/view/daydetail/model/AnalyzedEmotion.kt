package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail

data class AnalyzedEmotion(
    val emotion: Emotion,
    val proportion: Int
) {
    companion object {
        fun fromResponse(response: CalendarDayDetail.EmotionProportion): AnalyzedEmotion =
            AnalyzedEmotion(response.emotion, response.percent)
    }
}