package com.rtsoju.mongle.domain.usecase.kakaotalk

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.KakaotalkRepository
import java.io.InputStream
import javax.inject.Inject

/**
 * 카카오톡 텍스트를 보내면 백엔드 단에서 s3 스토리지에 올린다.
 */
class UploadKakaotalkUseCase @Inject constructor(
    private val kakaotalkRepository: KakaotalkRepository
) {
    suspend operator fun invoke(
        roomName: String,
        stream: InputStream
    ): Result<MessageResult> {
        return kakaotalkRepository.upload(roomName, stream)
    }
}