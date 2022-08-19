package com.won983212.mongle.data.remote.datasource

import com.won983212.mongle.common.util.NetworkErrorHandler
import com.won983212.mongle.common.util.safeApiCall
import com.won983212.mongle.data.remote.api.KakaoSendApi
import com.won983212.mongle.data.remote.model.ResponseMessage
import com.won983212.mongle.repository.TokenRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class KakaoSendDataSource @Inject constructor(
    private val kakaoSendApi: KakaoSendApi,
    private val tokenRepository: TokenRepository
) {
    suspend fun sendKakaoTalkData(
        errorHandler: NetworkErrorHandler,
        content: ByteArray
    ): ResponseMessage? {
        val fileBody = RequestBody.create(MediaType.parse("text/plain"), content)
        val filePart = MultipartBody.Part.createFormData("files", "KakaoData.txt", fileBody)
        return safeApiCall(errorHandler) {
            kakaoSendApi.sendTalkFile(
                tokenRepository.getToken().accessToken,
                filePart
            )
        }
    }
}