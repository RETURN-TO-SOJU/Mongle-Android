package com.won983212.mongle.domain.usecase.user

import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 탈퇴하기 */
class LeaveAccountUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Result<MessageResult> {
        return userRepository.leaveAccount()
    }
}