package com.won983212.mongle.domain.usecase.user

import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 이름 설정 */
class SetUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): Result<MessageResult> {
        return userRepository.setUsername(username)
    }
}