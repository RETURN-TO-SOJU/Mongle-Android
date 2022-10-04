package com.won983212.mongle.domain.usecase.password

import com.won983212.mongle.domain.repository.PasswordRepository
import javax.inject.Inject

/** 데이터를 암호화하기 위한 비밀번호를 설정합니다. 만약 null을 전달하면 비밀번호를 삭제합니다. */
class SetDataKeyPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(password: String?) {
        return passwordRepository.setDataKeyPassword(password)
    }
}