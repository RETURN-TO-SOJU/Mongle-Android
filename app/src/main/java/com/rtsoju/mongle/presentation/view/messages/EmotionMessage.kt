package com.rtsoju.mongle.presentation.view.messages

import com.rtsoju.mongle.domain.model.Emotion

data class EmotionMessage(
    val emotion: Emotion,
    val message: String
)