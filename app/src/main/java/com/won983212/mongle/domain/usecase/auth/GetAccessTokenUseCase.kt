package com.won983212.mongle.domain.usecase.auth

import com.won983212.mongle.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * 몽글 AccessToken 가져오기.
 * 토큰이 만료되었거나 없다면 발급까지 자동으로 진행한다.
 */
class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<String> {
        return authRepository.getAccessToken()
    }
}