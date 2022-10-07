package com.won983212.mongle.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class MessageResult(
    @SerializedName("message")
    val message: String
)