package com.won983212.mongle.data.remote.api

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.request.FCMTokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    @POST("login/kakao")
    suspend fun login(@Body token: OAuthLoginToken): OAuthLoginToken

    @GET("users/info")
    suspend fun getUserInfo(@Header(AUTH_TOKEN_HEADER) token: String): User

    @GET("users/fcmtoken")
    suspend fun setFCMToken(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Body fcmToken: FCMTokenRequest
    ): MessageResult
}