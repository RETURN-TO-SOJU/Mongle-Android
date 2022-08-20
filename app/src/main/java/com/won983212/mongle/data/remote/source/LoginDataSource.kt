package com.won983212.mongle.data.remote.source

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.data.remote.api.LoginApi
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.safeApiCall
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginApi: LoginApi
) {
    suspend fun login(
        requestLifecycleCallback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(requestLifecycleCallback) {
            loginApi.login(kakaoToken)
        }
    }
}