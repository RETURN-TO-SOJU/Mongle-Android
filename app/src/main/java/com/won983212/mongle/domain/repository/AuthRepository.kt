package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.remote.model.response.LoginResponse

interface AuthRepository {

    /**
     * 몽글 AccessToken 가져오기.
     * 토큰이 만료되었거나 없다면 발급까지 자동으로 진행한다.
     */
    suspend fun getAccessToken(): Result<String>

    /**
     * 저장된 토큰 삭제
     */
    fun clear()

    /**
     * 몽글 Token refresh
     */
    suspend fun refreshToken(): Result<OAuthLoginToken>

    /**
     * 카카오 access & refresh 토큰을 사용한 회원가입 및 로그인
     */
    suspend fun login(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResponse>
}