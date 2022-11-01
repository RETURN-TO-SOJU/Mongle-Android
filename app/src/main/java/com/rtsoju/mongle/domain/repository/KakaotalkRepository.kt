package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import java.io.InputStream

interface KakaotalkRepository {
    suspend fun upload(
        roomName: String,
        stream: InputStream
    ): Result<MessageResult>
}