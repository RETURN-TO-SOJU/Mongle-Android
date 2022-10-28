package com.rtsoju.mongle.data.source.remote

import com.rtsoju.mongle.data.source.remote.api.KakaoSendApi
import com.rtsoju.mongle.data.source.remote.api.safeApiCall
import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

internal class RemoteKakaotalkDataSource @Inject constructor(
    private val api: KakaoSendApi,
) {
    suspend fun upload(
        roomName: String,
        content: ByteArray
    ): Result<MessageResult> {
        val fileBody = content.toRequestBody("text/plain".toMediaTypeOrNull(), 0, content.size)
        val filePart = MultipartBody.Part.createFormData("files", "$roomName.txt", fileBody)
        return safeApiCall {
            api.uploadKakaotalk(
                filePart
            )
        }
    }
}