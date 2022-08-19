package com.won983212.mongle.data.repository

import com.won983212.mongle.common.util.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.ResponseMessage
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
    ): ResponseMessage? = kakaotalkDataSource.upload(requestLifecycleCallback, content)
}