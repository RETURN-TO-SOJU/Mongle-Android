package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.dto.request.UsernameRequest
import com.won983212.mongle.data.source.remote.dto.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/info")
    suspend fun getUserInfo(): UserResponse

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