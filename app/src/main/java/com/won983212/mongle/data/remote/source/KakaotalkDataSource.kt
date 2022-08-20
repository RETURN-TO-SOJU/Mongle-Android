package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.remote.api.KakaoSendApi
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.safeApiCall
import com.won983212.mongle.data.remote.model.PlainMessage
import com.won983212.mongle.repository.AuthRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class KakaotalkDataSource @Inject constructor(
    private val kakaoSendApi: KakaoSendApi,
    private val authRepository: AuthRepository
) {
    suspend fun upload(
        errorHandler: RequestLifecycleCallback,
        content: ByteArray
    ): PlainMessage? {
        val fileBody = RequestBody.create(MediaType.parse("text/plain"), content)
        val filePart = MultipartBody.Part.createFormData("files", "KakaoData.txt", fileBody)
        return safeApiCall(errorHandler) {
            kakaoSendApi.uploadKakaotalk(
                authRepository.getCurrentToken().accessToken,
                filePart
            )
        }
    }
}