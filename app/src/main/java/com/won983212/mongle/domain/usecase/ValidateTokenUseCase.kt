package com.won983212.mongle.domain.usecase

import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(callback: RequestLifecycleCallback): Boolean {
        val user = userRepository.getUserInfo(callback)
        return user != null || userRepository.refreshToken(callback) != null
    }
}