package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.data.util.CachePolicy
import com.won983212.mongle.domain.model.User

interface UserRepository {

    suspend fun getUserInfo(cachePolicy: CachePolicy): Result<User>

    suspend fun setFCMToken(
        fcmToken: String
    ): Result<MessageResult>

    suspend fun setUsername(
        username: String
    ): Result<MessageResult>

    suspend fun leaveAccount(): Result<MessageResult>
}