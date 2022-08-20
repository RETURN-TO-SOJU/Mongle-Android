package com.won983212.mongle.repository

import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.model.PlainMessage

interface KakaotalkRepository {
    suspend fun upload(
        requestLifecycleCallback: RequestLifecycleCallback,
        content: ByteArray
    ): PlainMessage?
}