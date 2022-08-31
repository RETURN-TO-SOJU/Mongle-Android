package com.won983212.mongle.domain.usecase

import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.exception.NeedsLoginException
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    /**
     * TODO 이 부분도 Callback 문제가 해결되면 적절하게 수정해야한다.
     * Token이 실제로 유효한지 서버에 요청을 보내본다.
     * 만약 유효한 토큰이라면 true, 아니면 false를 리턴한다.
     */
    suspend fun execute(callback: RequestLifecycleCallback): Boolean {
        return try {
            val user = userRepository.getUserInfo(callback)
            user != null || authRepository.refreshToken(callback) != null
        } catch (e: NeedsLoginException) {
            false
        }
    }
}