package com.rtsoju.mongle.data.source.remote.dto

import com.google.gson.annotations.SerializedName

data class MessageResult(
    @SerializedName("message")
    val message: String
)