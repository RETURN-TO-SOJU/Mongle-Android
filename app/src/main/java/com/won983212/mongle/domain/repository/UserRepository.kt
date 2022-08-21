package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback

interface UserRepository {
    fun getCurrentToken(): OAuthLoginToken

    fun setCurrentToken(token: OAuthLoginToken)

    suspend fun getUserInfo(
        callback: RequestLifecycleCallback
    ): User?

    /**
     * 카카오 access & refresh 토큰을 사용한 회원가입 및 로그인
     */
    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): OAuthLoginToken?
}