package com.rtsoju.mongle.domain.usecase.password

import com.rtsoju.mongle.domain.repository.PasswordRepository
import javax.inject.Inject

/** 화면잠금 비밀번호를 설정합니다. 만약 null을 전달하면 비밀번호를 삭제합니다. */
class SetScreenPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(password: String?) {
        return passwordRepository.setScreenPassword(password)
    }
}