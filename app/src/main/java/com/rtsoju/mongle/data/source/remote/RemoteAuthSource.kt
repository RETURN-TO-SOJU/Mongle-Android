package com.rtsoju.mongle.data.source.remote

import com.rtsoju.mongle.data.mapper.toDomainModel
import com.rtsoju.mongle.data.source.api.AuthApi
import com.rtsoju.mongle.data.source.api.safeApiCall
import com.rtsoju.mongle.domain.model.LoginResult
import com.rtsoju.mongle.domain.model.OAuthLoginToken
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