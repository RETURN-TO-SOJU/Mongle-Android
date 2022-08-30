package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.source.api.KakaoSendApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class MockKakaoSendApi : KakaoSendApi {
    override suspend fun uploadKakaotalk(
        token: String,
        files: MultipartBody.Part
    ): MessageResult =
        withContext(Dispatchers.IO) {
            MockUserApi.checkToken(token)
            delay(1000)
            MessageResult("complete")
        }
}