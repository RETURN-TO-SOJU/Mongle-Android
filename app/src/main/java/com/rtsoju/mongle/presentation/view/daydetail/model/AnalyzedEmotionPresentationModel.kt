package com.rtsoju.mongle.presentation.view.daydetail.model

import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.model.EmotionProportion

data class AnalyzedEmotionPresentationModel(
    val emotion: Emotion,
    val proportion: Int
) {
    companion object {
        fun fromDomainModel(response: EmotionProportion): AnalyzedEmotionPresentationModel =
            AnalyzedEmotionPresentationModel(response.emotion, response.percent)
    }
}