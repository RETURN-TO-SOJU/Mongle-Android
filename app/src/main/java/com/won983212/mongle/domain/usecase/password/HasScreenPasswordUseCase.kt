package com.won983212.mongle.domain.usecase.password

import com.won983212.mongle.domain.repository.PasswordRepository
import javax.inject.Inject

/** 화면잠금 비밀번호가 설정되어있는지 확인합니다. */
class HasScreenPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(): Boolean {
        return passwordRepository.hasScreenPassword()
    }
}