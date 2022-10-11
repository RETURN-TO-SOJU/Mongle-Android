package com.rtsoju.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("name")
    val username: String,
    @SerializedName("nickName")
    val kakaoName: String
)