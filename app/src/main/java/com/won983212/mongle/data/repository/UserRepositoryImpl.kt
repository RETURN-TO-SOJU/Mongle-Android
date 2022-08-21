package com.won983212.mongle.data.repository

import com.won983212.mongle.data.local.source.TokenDataSource
import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.source.RemoteUserDataSource
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl
@Inject constructor(
    private val tokenDataSource: TokenDataSource,
    private val userDataSource: RemoteUserDataSource
) : UserRepository {

    override fun getCurrentToken(): OAuthLoginToken = tokenDataSource.getToken()

    override fun setCurrentToken(token: OAuthLoginToken) = tokenDataSource.setToken(token)

    override suspend fun getUserInfo(
        callback: RequestLifecycleCallback
    ): User? = userDataSource.getUserInfo(callback, getCurrentToken())

    override suspend fun refreshToken(
        callback: RequestLifecycleCallback
    ): OAuthLoginToken? {
        val token = userDataSource.refreshToken(callback, getCurrentToken())
        if (token != null) {
            setCurrentToken(token)
        }
        return token
    }

    override suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? = userDataSource.login(callback, kakaoToken)

    override suspend fun setFCMToken(
        callback: RequestLifecycleCallback,
        fcmToken: String
    ): MessageResult? =
        userDataSource.setFCMToken(callback, getCurrentToken().accessToken, fcmToken)
}