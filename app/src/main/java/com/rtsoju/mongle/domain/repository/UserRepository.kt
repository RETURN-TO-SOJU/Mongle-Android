package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.User

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