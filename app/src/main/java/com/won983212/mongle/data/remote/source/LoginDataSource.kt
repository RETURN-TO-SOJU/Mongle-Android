package com.won983212.mongle.data.remote.source

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.common.util.RequestLifecycleCallback
import com.won983212.mongle.common.util.safeApiCall
import com.won983212.mongle.data.remote.api.LoginApi
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