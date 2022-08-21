package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.remote.api.KakaoSendApi
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.safeApiCall
import com.won983212.mongle.data.remote.model.MessageResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RemoteKakaotalkDataSource @Inject constructor(
    private val api: KakaoSendApi,
) {
    suspend fun upload(
        errorHandler: RequestLifecycleCallback,
        accessToken: String,
        content: ByteArray
    ): MessageResult? {
        val fileBody = content.toRequestBody("text/plain".toMediaTypeOrNull(), 0, content.size)
        val filePart = MultipartBody.Part.createFormData("files", "KakaoData.txt", fileBody)
        return safeApiCall(errorHandler) {
            api.uploadKakaotalk(
                accessToken,
                filePart
            )
        }
    }
}