package com.rtsoju.mongle.data.source.remote.dto.request

import com.google.gson.annotations.SerializedName

data class FCMTokenRequest(
    @SerializedName("fcmToken")
    val fcmToken: String
)
