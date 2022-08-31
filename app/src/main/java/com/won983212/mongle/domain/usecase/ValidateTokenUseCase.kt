package com.won983212.mongle.domain.usecase

import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.exception.NeedsLoginException
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    /**
     * Token이 실제로 유효한지 서버에 요청을 보내본다.
     * 만약 유효한 토큰이라면 true, 아니면 false를 리턴한다.
     */
    suspend fun execute(): Boolean {
        return try {
            val user = userRepository.getUserInfo()
            user.isSuccess || authRepository.refreshToken().isSuccess
        } catch (e: NeedsLoginException) {
            false
        }
    }
}