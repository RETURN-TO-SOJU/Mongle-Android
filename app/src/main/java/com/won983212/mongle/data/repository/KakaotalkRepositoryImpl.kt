package com.won983212.mongle.data.repository

import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.source.RemoteKakaotalkDataSource
import com.won983212.mongle.domain.repository.KakaotalkRepository
import javax.inject.Inject

internal class KakaotalkRepositoryImpl
@Inject constructor(
    private val kakaotalkDataSource: RemoteKakaotalkDataSource
) : KakaotalkRepository {

    override suspend fun upload(
        callback: RequestLifecycleCallback,
        content: ByteArray
    ): MessageResult? = kakaotalkDataSource.upload(callback, content)
}