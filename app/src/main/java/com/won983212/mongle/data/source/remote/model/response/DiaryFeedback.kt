package com.won983212.mongle.data.source.remote.model.response

import com.google.gson.annotations.SerializedName

data class DiaryFeedback(
    @SerializedName("answer")
    val answer: String
)