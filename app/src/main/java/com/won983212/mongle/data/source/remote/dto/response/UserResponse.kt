package com.won983212.mongle.data.source.remote.dto.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("name")
    val username: String,
    @SerializedName("kakaoName")
    val kakaoName: String
)