package com.won983212.mongle.repository

import com.won983212.mongle.common.util.NetworkErrorHandler
import com.won983212.mongle.data.remote.model.ResponseMessage

interface KakaotalkRepository {
    suspend fun upload(
        networkErrorHandler: NetworkErrorHandler,
        content: ByteArray
    ): ResponseMessage?
}