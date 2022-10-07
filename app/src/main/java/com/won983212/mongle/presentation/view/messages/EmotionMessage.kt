package com.won983212.mongle.presentation.view.messages

import com.won983212.mongle.domain.model.Emotion

data class EmotionMessage(
    val emotion: Emotion,
    val message: String
)