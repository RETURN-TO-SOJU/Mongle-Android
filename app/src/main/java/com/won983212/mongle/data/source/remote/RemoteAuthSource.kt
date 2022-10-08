package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.mapper.toDomainModel
import com.won983212.mongle.data.source.api.AuthApi
import com.won983212.mongle.data.source.api.safeApiCall
import com.won983212.mongle.domain.model.LoginResult
import com.won983212.mongle.domain.model.OAuthLoginToken
import javax.inject.Inject

class RemoteAuthSource @Inject constructor(
    private val api: AuthApi
) {
    suspend fun login(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResult> {
        return safeApiCall {
            api.login(kakaoToken).toDomainModel()
        }
    }

    suspend fun refreshToken(
        currentToken: OAuthLoginToken
    ): Result<OAuthLoginToken> {
        return safeApiCall {
            api.refreshToken(currentToken)
        }
    }
}