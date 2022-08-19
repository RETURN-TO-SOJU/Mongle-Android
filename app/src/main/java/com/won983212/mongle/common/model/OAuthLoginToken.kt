package com.won983212.mongle.common.model

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.model.OAuthToken

data class OAuthLoginToken(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
) {
    companion object {
        fun of(kakaoToken: OAuthToken) =
            OAuthLoginToken(kakaoToken.accessToken, kakaoToken.refreshToken)
    }
}