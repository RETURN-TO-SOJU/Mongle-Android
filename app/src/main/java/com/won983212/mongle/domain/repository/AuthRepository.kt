package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.remote.model.response.LoginResponse
import okhttp3.Interceptor
import okhttp3.Response

interface AuthRepository {

    /**
     * 몽글 AccessToken 가져오기.
     * 토큰이 만료되었거나 없다면 발급까지 자동으로 진행한다.
     */
    suspend fun getAccessToken(): String

    /**
     * 저장된 토큰 삭제
     */
    fun clear()

    /**
     * 몽글 Token refresh
     */
    suspend fun refreshToken(
        callback: RequestLifecycleCallback
    ): OAuthLoginToken?

    /**
     * 카카오 access & refresh 토큰을 사용한 회원가입 및 로그인
     */
    suspend fun login(
        callback: RequestLifecycleCallback,
        kakaoToken: OAuthLoginToken
    ): LoginResponse?
}