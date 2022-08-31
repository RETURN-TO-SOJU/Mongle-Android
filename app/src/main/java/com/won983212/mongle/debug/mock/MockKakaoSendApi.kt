package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.source.api.KakaoSendApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class MockKakaoSendApi : KakaoSendApi {
    override suspend fun uploadKakaotalk(
        files: MultipartBody.Part
    ): MessageResult =
        withContext(Dispatchers.IO) {
            delay(1000)
            MessageResult("complete")
        }
}