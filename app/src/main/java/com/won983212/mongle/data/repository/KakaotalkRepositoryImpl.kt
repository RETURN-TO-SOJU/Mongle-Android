package com.won983212.mongle.data.repository

import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.PlainMessage
import com.won983212.mongle.data.remote.source.KakaotalkDataSource
import com.won983212.mongle.repository.KakaotalkRepository
import javax.inject.Inject

internal class KakaotalkRepositoryImpl
@Inject constructor(
    private val kakaotalkDataSource: KakaotalkDataSource
) : KakaotalkRepository {

    override suspend fun upload(
        requestLifecycleCallback: RequestLifecycleCallback,
        content: ByteArray
    ): PlainMessage? = kakaotalkDataSource.upload(requestLifecycleCallback, content)
}