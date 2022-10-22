package com.rtsoju.mongle.data.source.remote.api

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface KakaoSendApi {
    @Multipart
    @POST("s3/kakao")
    suspend fun uploadKakaotalk(
        @Part files: MultipartBody.Part
    ): MessageResult
}