package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.remote.model.response.LoginResponse

interface AuthRepository {

    suspend fun getAccessToken(): Result<String>

    /** 저장된 토큰 삭제 */
    fun clear()

    /** 몽글 Token refresh */
    suspend fun refreshToken(): Result<OAuthLoginToken>

    suspend fun login(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResponse>
}