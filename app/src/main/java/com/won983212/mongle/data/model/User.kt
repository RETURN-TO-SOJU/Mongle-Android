package com.won983212.mongle.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String,
    @SerializedName("kakaoName")
    val kakaoName: String
)