package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.data.source.api.safeApiCall
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.FCMTokenRequest
import com.won983212.mongle.data.source.remote.model.request.UsernameRequest
import javax.inject.Inject

internal class RemoteUserDataSource @Inject constructor(
    private val api: UserApi
) {

    suspend fun getUserInfo(
        callback: RequestLifecycleCallback,
        accessToken: String
    ): User? {
        return safeApiCall(callback) {
            api.getUserInfo(accessToken)
        }
    }

    suspend fun setFCMToken(
        callback: RequestLifecycleCallback,
        accessToken: String,
        fcmToken: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.setFCMToken(accessToken, FCMTokenRequest(fcmToken))
        }
    }

    suspend fun setUsername(
        callback: RequestLifecycleCallback,
        accessToken: String,
        username: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.setUsername(accessToken, UsernameRequest(username))
        }
    }

    suspend fun leaveAccount(
        callback: RequestLifecycleCallback,
        accessToken: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.leaveAccount(accessToken)
        }
    }
}