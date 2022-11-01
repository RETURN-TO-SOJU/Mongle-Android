package com.rtsoju.mongle.data.source.remote

import com.rtsoju.mongle.data.mapper.toDomainModel
import com.rtsoju.mongle.data.source.remote.api.UserApi
import com.rtsoju.mongle.data.source.remote.api.safeApiCall
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.rtsoju.mongle.data.source.remote.dto.request.UsernameRequest
import com.rtsoju.mongle.domain.model.User
import javax.inject.Inject

internal class RemoteUserDataSource @Inject constructor(
    private val api: UserApi
) {

    suspend fun getUserInfo(): Result<User> {
        return safeApiCall {
            api.getUserInfo().toDomainModel()
        }
    }

    suspend fun setFCMToken(
        fcmToken: String
    ): Result<MessageResult> {
        return safeApiCall {
            api.setFCMToken(FCMTokenRequest(fcmToken))
        }
    }

    suspend fun setUsername(
        username: String
    ): Result<MessageResult> {
        return safeApiCall {
            api.setUsername(UsernameRequest(username))
        }
    }

    suspend fun leaveAccount(): Result<Unit> {
        return safeApiCall {
            api.leaveAccount()
        }
    }
}