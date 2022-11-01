package com.rtsoju.mongle.debug.mock

import com.rtsoju.mongle.data.source.remote.api.KakaoSendApi
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.domain.repository.AuthRepository
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