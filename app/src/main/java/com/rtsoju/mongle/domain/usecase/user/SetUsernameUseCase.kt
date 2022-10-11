package com.rtsoju.mongle.domain.usecase.user

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 이름 설정 */
class SetUsernameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(username: String): Result<MessageResult> {
        return userRepository.setUsername(username)
    }
}