package com.won983212.mongle.data.repository

import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.RemoteKakaotalkDataSource
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.KakaotalkRepository
import com.won983212.mongle.domain.repository.UserRepository
import javax.inject.Inject

internal class KakaotalkRepositoryImpl
@Inject constructor(
    private val kakaotalkDataSource: RemoteKakaotalkDataSource,
    private val authRepository: AuthRepository
) : KakaotalkRepository {

    override suspend fun upload(
        callback: RequestLifecycleCallback,
        content: ByteArray
    ): MessageResult? =
        kakaotalkDataSource.upload(callback, authRepository.getAccessToken(), content)
}