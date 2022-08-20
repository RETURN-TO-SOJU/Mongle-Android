package com.won983212.mongle.data.remote.model

import com.google.gson.annotations.SerializedName

data class PlainMessage(
    @SerializedName("message")
    val message: String
)
