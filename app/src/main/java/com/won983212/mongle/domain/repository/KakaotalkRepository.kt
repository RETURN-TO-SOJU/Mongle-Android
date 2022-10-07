package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.source.remote.dto.MessageResult

interface KakaotalkRepository {
    suspend fun upload(
        roomName: String,
        content: ByteArray
    ): Result<MessageResult>
}