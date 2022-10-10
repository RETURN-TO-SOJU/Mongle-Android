package com.won983212.mongle.domain.usecase.user

import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.FavoriteRepository
import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.domain.repository.UserRepository
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