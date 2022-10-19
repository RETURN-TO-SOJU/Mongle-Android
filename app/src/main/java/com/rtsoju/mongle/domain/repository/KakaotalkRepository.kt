package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.data.source.remote.dto.MessageResult

interface KakaotalkRepository {
    suspend fun upload(
        roomName: String,
        content: ByteArray
    ): Result<MessageResult>
}