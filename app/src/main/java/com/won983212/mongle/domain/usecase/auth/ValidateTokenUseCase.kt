package com.won983212.mongle.domain.usecase.auth

import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.usecase.user.GetUserInfoUseCase
import com.won983212.mongle.exception.NeedsLoginException
import javax.inject.Inject

/**
 * Token이 실제로 유효한지 서버에 요청을 보내본다.
 * 만약 유효한 토큰이라면 true, 아니면 false를 리턴한다.
 */
class ValidateTokenUseCase @Inject constructor(
    private val getUserInfo: GetUserInfoUseCase,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return try {
            val user = getUserInfo().getOrNull()
            if (user != null) {
                return user.username.isNotEmpty()
            }
            return authRepository.refreshToken().isSuccess
        } catch (e: NeedsLoginException) {
            false
        }
    }
}