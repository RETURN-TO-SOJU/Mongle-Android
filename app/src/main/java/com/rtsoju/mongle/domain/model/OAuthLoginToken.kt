package com.rtsoju.mongle.domain.model

import com.google.gson.annotations.SerializedName
import com.kakao.sdk.auth.model.OAuthToken
import com.rtsoju.mongle.util.parseLocalDateTime
import com.rtsoju.mongle.util.toLocalDateTime
import java.time.LocalDateTime

data class OAuthLoginToken(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("accessExpiredAt")
    val accessTokenExpiresAt: LocalDateTime,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("refreshExpiredAt")
    val refreshTokenExpiresAt: LocalDateTime,
) {
    fun isAccessTokenExpired(): Boolean {
        return LocalDateTime.now() > accessTokenExpiresAt
    }

    fun isRefreshTokenExpired(): Boolean {
        return LocalDateTime.now() > refreshTokenExpiresAt
    }

    companion object {
        fun fromKakaoToken(kakaoToken: OAuthToken) =
            OAuthLoginToken(
                kakaoToken.accessToken,
                kakaoToken.accessTokenExpiresAt.toLocalDateTime(),
                kakaoToken.refreshToken,
                kakaoToken.refreshTokenExpiresAt.toLocalDateTime()
            )

        fun fromLoginResult(response: LoginResult) =
            OAuthLoginToken(
                response.accessToken,
                response.accessTokenExpiresAt,
                response.refreshToken,
                response.refreshTokenExpiresAt
            )

        val EMPTY_TOKEN = OAuthLoginToken(
            "", parseLocalDateTime(0),
            "", parseLocalDateTime(0)
        )
    }
}