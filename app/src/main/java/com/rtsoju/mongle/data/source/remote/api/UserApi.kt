package com.rtsoju.mongle.data.source.remote.api

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.rtsoju.mongle.data.source.remote.dto.request.UsernameRequest
import com.rtsoju.mongle.data.source.remote.dto.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
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

    @DELETE("users")
    suspend fun leaveAccount(): MessageResult
}