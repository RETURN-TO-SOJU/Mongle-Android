package com.won983212.mongle.repository

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.common.util.NetworkErrorHandler

interface AuthRepository {
    fun getCurrentToken(): OAuthLoginToken

    fun setCurrentToken(token: OAuthLoginToken)

    suspend fun login(
        networkErrorHandler: NetworkErrorHandler,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken?
}