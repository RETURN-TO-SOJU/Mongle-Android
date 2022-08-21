package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.remote.api.LoginApi
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.safeApiCall
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val api: LoginApi
) {
    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(callback) {
            api.login(kakaoToken)
        }
    }
}