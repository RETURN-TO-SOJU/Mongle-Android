package com.won983212.mongle.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Api에서 반환값이 필요없고, 성공 여부만 알고싶을 때 이 클래스를 리턴 클래스로 사용
 */
data class MessageResult(
    @SerializedName("message")
    val message: String
)
