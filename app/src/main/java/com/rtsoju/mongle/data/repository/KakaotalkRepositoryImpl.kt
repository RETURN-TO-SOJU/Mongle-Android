package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.PasswordDataSource
import com.rtsoju.mongle.data.source.remote.RemoteKakaotalkDataSource
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.KakaotalkRepository
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

internal class KakaotalkRepositoryImpl
@Inject constructor(
    private val kakaotalkDataSource: RemoteKakaotalkDataSource,
    private val passwordDataSource: PasswordDataSource
) : KakaotalkRepository {

    override suspend fun upload(roomName: String, stream: InputStream): Result<MessageResult> {
        val reader = InputStreamReader(stream, PasswordRepositoryImpl.PASSWORD_CHARSET)
        val contentBuilder = StringBuilder()
        contentBuilder.append(reader.readText())
        contentBuilder.append('\n')
        contentBuilder.append(passwordDataSource.getDataKeyPassword())

        val data = contentBuilder.toString().toByteArray(PasswordRepositoryImpl.PASSWORD_CHARSET)
        return kakaotalkDataSource.upload(roomName, data)
    }
}