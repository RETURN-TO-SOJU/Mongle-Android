package com.won983212.mongle.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class FCMTokenRequest(
    @SerializedName("token")
    val token: String
)
