package com.rtsoju.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName
import com.rtsoju.mongle.domain.model.Emotion

data class EmotionalSentenceResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("sentence")
    val sentence: String,
    @SerializedName("emotion")
    val emotion: Emotion,
    @SerializedName("roomName")
    val roomName: String
)