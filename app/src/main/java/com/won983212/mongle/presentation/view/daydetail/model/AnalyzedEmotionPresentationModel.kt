package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.EmotionProportion

data class AnalyzedEmotionPresentationModel(
    val emotion: Emotion,
    val proportion: Int
) {
    companion object {
        fun fromDomainModel(response: EmotionProportion): AnalyzedEmotionPresentationModel =
            AnalyzedEmotionPresentationModel(response.emotion, response.percent)
    }
}