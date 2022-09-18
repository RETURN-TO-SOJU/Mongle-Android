package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.source.api.KakaoSendApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

class MockKakaoSendApi(
    private val authRepository: AuthRepository
) : KakaoSendApi {

    override suspend fun uploadKakaotalk(
        files: MultipartBody.Part
    ): MessageResult =
        withContext(Dispatchers.IO) {
            MockAuthApi.checkToken(authRepository)
            MessageResult("complete")
        }
}