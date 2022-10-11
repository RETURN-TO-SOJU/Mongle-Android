package com.rtsoju.mongle.domain.usecase.password

import com.rtsoju.mongle.domain.repository.PasswordRepository
import javax.inject.Inject

/** 화면잠금 비밀번호가 일치하는지 확인합니다. */
class CheckScreenPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(password: String): Boolean {
        return passwordRepository.checkScreenPassword(password)
    }
}