package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.model.request.UsernameRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/info")
    suspend fun getUserInfo(): User

    @POST("users/fcm")
    suspend fun setFCMToken(
        @Body fcmToken: FCMTokenRequest
    ): MessageResult

    @POST("users/name")
    suspend fun setUsername(
        @Body username: UsernameRequest
    ): MessageResult

    @POST("leave")
    suspend fun leaveAccount(): MessageResult
}