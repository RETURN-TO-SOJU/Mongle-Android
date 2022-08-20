package com.won983212.mongle.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class DiaryRequest(
    @SerializedName("text")
    val text: String
)
