package com.won983212.mongle.data.remote.api

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    @POST("login/kakao")
    suspend fun login(@Body token: OAuthLoginToken): OAuthLoginToken

    @GET("user")
    suspend fun getUserInfo(@Body token: OAuthLoginToken): User
}