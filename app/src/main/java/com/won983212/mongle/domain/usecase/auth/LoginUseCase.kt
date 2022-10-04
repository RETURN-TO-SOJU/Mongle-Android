package com.won983212.mongle.domain.usecase.auth

import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.source.remote.model.response.LoginResponse
import com.won983212.mongle.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 카카오 access & refresh 토큰을 사용한 회원가입 및 로그인
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResponse> {
        return authRepository.login(kakaoToken)
    }
}