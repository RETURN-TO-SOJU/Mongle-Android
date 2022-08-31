package com.won983212.mongle.data.source.remote

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
        callback: RequestLifecycleCallback
    ): User? {
        return safeApiCall(callback) {
            api.getUserInfo()
        }
    }

    suspend fun setFCMToken(
        callback: RequestLifecycleCallback,
        fcmToken: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.setFCMToken(FCMTokenRequest(fcmToken))
        }
    }

    // TODO Username도 Set하면 local에 저장
    suspend fun setUsername(
        callback: RequestLifecycleCallback,
        username: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.setUsername(UsernameRequest(username))
        }
    }

    suspend fun leaveAccount(
        callback: RequestLifecycleCallback
    ): MessageResult? {
        return safeApiCall(callback) {
            api.leaveAccount()
        }
    }
}