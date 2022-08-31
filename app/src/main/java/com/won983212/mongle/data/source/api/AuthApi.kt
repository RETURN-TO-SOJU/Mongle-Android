package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.remote.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login/kakao")
    suspend fun login(@Body kakaoToken: OAuthLoginToken): LoginResponse

    @POST("reissue/token")
    suspend fun refreshToken(@Body token: OAuthLoginToken): OAuthLoginToken
}