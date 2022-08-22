package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.UserApi
import com.won983212.mongle.data.remote.api.safeApiCall
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.request.FCMTokenRequest
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
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

    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(callback) {
            api.login(kakaoToken)
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

    suspend fun refreshToken(
        callback: RequestLifecycleCallback,
        currentToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(callback) {
            api.refreshToken(currentToken)
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