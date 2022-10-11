package com.rtsoju.mongle.domain.usecase.user

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.AuthRepository
import com.rtsoju.mongle.domain.repository.FavoriteRepository
import com.rtsoju.mongle.domain.repository.PasswordRepository
import com.rtsoju.mongle.domain.repository.UserRepository
import javax.inject.Inject

/** 탈퇴하기 */
class LeaveAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val passwordRepository: PasswordRepository,
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(): Result<MessageResult> {
        passwordRepository.setDataKeyPassword(null)
        passwordRepository.setScreenPassword(null)
        favoriteRepository.clear()
        authRepository.clear()
        return userRepository.leaveAccount()
    }
}