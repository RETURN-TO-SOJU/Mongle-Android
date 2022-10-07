package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.data.source.api.safeApiCall
import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.data.source.remote.dto.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.dto.request.UsernameRequest
import com.won983212.mongle.data.util.toDomainModel
import com.won983212.mongle.domain.model.User
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

    suspend fun leaveAccount(): Result<MessageResult> {
        return safeApiCall {
            api.leaveAccount()
        }
    }
}