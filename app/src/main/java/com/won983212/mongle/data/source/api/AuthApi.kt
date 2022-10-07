package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.di.NoAuthorization
import com.won983212.mongle.data.source.remote.model.OAuthLoginToken
import com.won983212.mongle.data.source.remote.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login/kakao")
    @NoAuthorization
    suspend fun login(@Body kakaoToken: OAuthLoginToken): LoginResponse

    @POST("reissue/token")
    @NoAuthorization
    suspend fun refreshToken(@Body token: OAuthLoginToken): OAuthLoginToken
}