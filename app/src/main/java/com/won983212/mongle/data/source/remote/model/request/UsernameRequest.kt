package com.won983212.mongle.data.source.remote.model.request

import com.google.gson.annotations.SerializedName

data class UsernameRequest(
    @SerializedName("userName")
    val username: String
)
