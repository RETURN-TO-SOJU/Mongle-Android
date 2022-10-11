package com.rtsoju.mongle.domain.usecase.password

import com.rtsoju.mongle.domain.repository.PasswordRepository
import java.io.InputStream
import javax.inject.Inject

/** 서버에 전송할, 비밀번호가 포함된 카카오톡 데이터 패킷을 만듭니다. */
class MakePwdKakaotalkDataPacketUseCase @Inject constructor(
    private val passwordRepository: PasswordRepository
) {
    operator fun invoke(dataStream: InputStream): ByteArray {
        return passwordRepository.makePwdKakaotalkDataPacket(dataStream)
    }
}