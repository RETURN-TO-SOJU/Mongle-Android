package com.won983212.mongle.repository

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback

interface AuthRepository {
    fun getCurrentToken(): OAuthLoginToken

    fun setCurrentToken(token: OAuthLoginToken)

    suspend fun login(
        requestLifecycleCallback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken?
}