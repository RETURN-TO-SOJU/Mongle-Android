package com.won983212.mongle.data.remote.datasource

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.common.util.NetworkErrorHandler
import com.won983212.mongle.common.util.safeApiCall
import com.won983212.mongle.data.remote.api.LoginApi
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginApi: LoginApi
) {
    suspend fun login(
        networkErrorHandler: NetworkErrorHandler,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        return safeApiCall(networkErrorHandler) {
            loginApi.login(kakaoToken)
        }
    }
}