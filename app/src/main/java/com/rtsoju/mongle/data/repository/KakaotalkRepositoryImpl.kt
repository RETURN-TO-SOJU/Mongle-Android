package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.remote.RemoteKakaotalkDataSource
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.KakaotalkRepository
import javax.inject.Inject

internal class KakaotalkRepositoryImpl
@Inject constructor(
    private val kakaotalkDataSource: RemoteKakaotalkDataSource
) : KakaotalkRepository {

    override suspend fun upload(roomName: String, content: ByteArray): Result<MessageResult> =
        kakaotalkDataSource.upload(roomName, content)
}