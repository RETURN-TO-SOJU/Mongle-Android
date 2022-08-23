package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.source.remote.model.MessageResult
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface KakaoSendApi {
    @Multipart
    @POST("s3/kakao")
    suspend fun uploadKakaotalk(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Part files: MultipartBody.Part
    ): MessageResult
}