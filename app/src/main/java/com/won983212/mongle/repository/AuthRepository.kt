package com.won983212.mongle.repository

import com.won983212.mongle.common.model.OAuthLoginToken
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback

interface AuthRepository {
    fun getCurrentToken(): OAuthLoginToken

    fun setCurrentToken(token: OAuthLoginToken)

    /**
     * 카카오 access & refresh 토큰을 사용한 회원가입 및 로그인
     */
    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken?
}