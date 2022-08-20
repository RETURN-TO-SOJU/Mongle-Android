package com.won983212.mongle.data.repository

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.data.local.source.TokenDataSource
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.source.LoginDataSource
import com.won983212.mongle.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl
@Inject constructor(
    private val tokenDataSource: TokenDataSource,
    private val loginDataSource: LoginDataSource
) : AuthRepository {

    override fun getCurrentToken(): OAuthLoginToken = tokenDataSource.getToken()

    override fun setCurrentToken(token: OAuthLoginToken) = tokenDataSource.setToken(token)

    override suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? = loginDataSource.login(callback, kakaoToken)
}