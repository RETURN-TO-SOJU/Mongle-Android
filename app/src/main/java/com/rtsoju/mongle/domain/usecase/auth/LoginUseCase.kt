package com.rtsoju.mongle.domain.usecase.auth

import com.rtsoju.mongle.domain.model.LoginResult
import com.rtsoju.mongle.domain.model.OAuthLoginToken
import com.rtsoju.mongle.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 카카오 access & refresh 토큰을 사용한 회원가입 및 로그인
 */
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        kakaoToken: OAuthLoginToken
    ): Result<LoginResult> {
        return authRepository.login(kakaoToken)
    }
}