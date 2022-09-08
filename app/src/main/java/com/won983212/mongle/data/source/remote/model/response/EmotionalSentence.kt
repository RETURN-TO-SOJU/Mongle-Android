package com.won983212.mongle.data.source.remote.model.response

import com.google.gson.annotations.SerializedName
import com.won983212.mongle.data.model.Emotion

data class EmotionalSentence(
    @SerializedName("id")
    val id: Long,
    @SerializedName("sentence")
    val sentence: String,
    @SerializedName("emotion")
    val emotion: Emotion
)
