package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.remote.model.MessageResult

interface UserRepository {

    suspend fun getUserInfo(): Result<User>

    suspend fun setFCMToken(
        fcmToken: String
    ): Result<MessageResult>

    suspend fun setUsername(
        username: String
    ): Result<MessageResult>

    suspend fun leaveAccount(): Result<MessageResult>
}