package com.won983212.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.EmotionalSentence

data class EmotionalSentenceResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("sentence")
    val sentence: String,
    @SerializedName("emotion")
    val emotion: Emotion
)