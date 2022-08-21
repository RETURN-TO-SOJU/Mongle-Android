package com.won983212.mongle.data.repository

import com.won983212.mongle.data.local.source.TokenDataSource
import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.source.RemoteUserDataSource
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl
@Inject constructor(
    private val tokenDataSource: TokenDataSource,
    private val userDataSource: RemoteUserDataSource
) : UserRepository {

    // TODO validate token logic 추가
    override fun getCurrentToken(): OAuthLoginToken = tokenDataSource.getToken()

    override fun setCurrentToken(token: OAuthLoginToken) = tokenDataSource.setToken(token)

    override suspend fun getUserInfo(
        callback: RequestLifecycleCallback
    ): User? = userDataSource.getUserInfo(callback, getCurrentToken())

    override suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? = userDataSource.login(callback, kakaoToken)
}