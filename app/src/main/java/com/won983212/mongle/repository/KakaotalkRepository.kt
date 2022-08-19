package com.won983212.mongle.repository

import com.won983212.mongle.common.util.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.ResponseMessage

interface KakaotalkRepository {
    suspend fun upload(
        requestLifecycleCallback: RequestLifecycleCallback,
        content: ByteArray
    ): ResponseMessage?
}