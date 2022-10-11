package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.domain.model.LoginResult
import com.rtsoju.mongle.domain.model.OAuthLoginToken

interface AuthRepository {

    suspend fun getAccessToken(): Result<String>

    /** 저장된 토큰 삭제 */
    fun clear()

    /** 몽글 Token refresh */
    suspend fun refreshToken(): Result<OAuthLoginToken>

    suspend fun login(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResult>
}