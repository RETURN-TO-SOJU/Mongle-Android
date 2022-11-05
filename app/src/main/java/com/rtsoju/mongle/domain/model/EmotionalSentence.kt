package com.rtsoju.mongle.domain.model

data class EmotionalSentence(
    val sentence: String,
    val emotion: Emotion,
    val roomName: String
)
