package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.local.LocalTokenSource
import com.rtsoju.mongle.data.source.remote.RemoteAuthSource
import com.rtsoju.mongle.domain.model.LoginResult
import com.rtsoju.mongle.domain.model.OAuthLoginToken
import com.rtsoju.mongle.domain.repository.AuthRepository
import com.rtsoju.mongle.exception.NeedsLoginException
import javax.inject.Inject

internal class AuthRepositoryImpl
@Inject constructor(
    private val localTokenSource: LocalTokenSource,
    private val remoteAuthSource: RemoteAuthSource
) : AuthRepository {

    override suspend fun getAccessToken(): Result<String> {
        val localToken = localTokenSource.getToken()
        if (localToken != OAuthLoginToken.EMPTY_TOKEN) {
            if (localToken.isAccessTokenExpired()) {
                val token = refreshToken()
                token.onSuccess {
                    return Result.success(token.getOrThrow().accessToken)
                }
            } else {
                return Result.success(localToken.accessToken)
            }
        }
        return Result.failure(NeedsLoginException())
    }

    override fun clear() {
        localTokenSource.clear()
    }

    override suspend fun refreshToken(): Result<OAuthLoginToken> {
        val token = remoteAuthSource.refreshToken(localTokenSource.getToken())
        token.onSuccess {
            localTokenSource.setToken(token.getOrThrow())
        }
        return token
    }

    override suspend fun login(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResult> {
        val response = remoteAuthSource.login(kakaoToken)
        response.onSuccess {
            localTokenSource.setToken(OAuthLoginToken.fromLoginResult(response.getOrThrow()))
        }
        return response
    }
}