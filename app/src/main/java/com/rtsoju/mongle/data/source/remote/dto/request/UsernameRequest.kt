package com.rtsoju.mongle.data.source.remote.dto.request

import com.google.gson.annotations.SerializedName

data class UsernameRequest(
    @SerializedName("userName")
    val username: String
)
