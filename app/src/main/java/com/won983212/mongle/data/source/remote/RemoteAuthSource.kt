package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.api.AuthApi
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.api.safeApiCall
import javax.inject.Inject

class RemoteAuthSource @Inject constructor(
    private val api: AuthApi
) {
    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(callback) {
            api.login(kakaoToken)
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
}