package com.rtsoju.mongle.data.source.remote.dto.request

import com.google.gson.annotations.SerializedName

data class DiaryRequest(
    @SerializedName("text")
    val text: String,
    @SerializedName("key")
    val password: String
)
