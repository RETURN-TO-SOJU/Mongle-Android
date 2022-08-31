package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.api.EmptyRequestLifecycleCallback
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.local.LocalTokenSource
import com.won983212.mongle.data.source.remote.RemoteAuthSource
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.exception.NeedsLoginException
import javax.inject.Inject

internal class AuthRepositoryImpl
@Inject constructor(
    private val localTokenSource: LocalTokenSource,
    private val remoteAuthSource: RemoteAuthSource
) : AuthRepository {

    override suspend fun getAccessToken(): String {
        val localToken = localTokenSource.getToken()
        if (localToken != OAuthLoginToken.EMPTY_TOKEN) {
            if (localToken.isAccessTokenExpired()) {
                // TODO 반드시 개선해야함. 에러 핸들링 방식을 바꾸고 나서 아래 코드도 수정 바람
                val token = refreshToken(EmptyRequestLifecycleCallback)
                if (token != null) {
                    return token.accessToken
                }
            } else {
                return localToken.accessToken
            }
        }
        throw NeedsLoginException()
    }

    override fun clear() {
        localTokenSource.setToken(OAuthLoginToken.EMPTY_TOKEN)
    }

    override suspend fun refreshToken(
        callback: RequestLifecycleCallback
    ): OAuthLoginToken? {
        val token = remoteAuthSource.refreshToken(callback, localTokenSource.getToken())
        if (token != null) {
            localTokenSource.setToken(token)
        }
        return token
    }

    override suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken? {
        val token = remoteAuthSource.login(callback, kakaoToken)
        if (token != null) {
            localTokenSource.setToken(token)
        }
        return token
    }
}