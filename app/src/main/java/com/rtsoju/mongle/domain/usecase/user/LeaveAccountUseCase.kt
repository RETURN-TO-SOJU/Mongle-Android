package com.rtsoju.mongle.domain.usecase.user

import com.rtsoju.mongle.domain.repository.*
import javax.inject.Inject

/** 탈퇴하기 */
class LeaveAccountUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val configRepository: ConfigRepository,
    private val passwordRepository: PasswordRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(): Result<Unit> {
        val result = userRepository.leaveAccount()
        passwordRepository.setDataKeyPassword(null)
        passwordRepository.setScreenPassword(null)
        favoriteRepository.clear()
        authRepository.clear()
        configRepository.editor().clear().apply()
        return result
    }
}