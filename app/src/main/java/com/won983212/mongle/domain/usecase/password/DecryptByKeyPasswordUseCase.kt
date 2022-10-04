package com.won983212.mongle.domain.usecase.password

import com.won983212.mongle.domain.repository.PasswordRepository
import javax.inject.Inject

/** 암호화되어있는 데이터를 키 비밀번호로 복호화합니다. */
class DecryptByKeyPasswordUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(data: String): String {
        return passwordRepository.decryptByKeyPassword(data)
    }
}