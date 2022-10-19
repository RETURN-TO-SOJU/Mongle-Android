package com.rtsoju.mongle.data.source.api

import com.rtsoju.mongle.data.di.NoAuthorization
import com.rtsoju.mongle.data.source.remote.dto.response.LoginResponse
import com.rtsoju.mongle.domain.model.OAuthLoginToken
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login/kakao")
    @NoAuthorization
    suspend fun login(@Body kakaoToken: OAuthLoginToken): LoginResponse

    @POST("reissue/token")
    @NoAuthorization
    suspend fun refreshToken(@Body token: OAuthLoginToken): OAuthLoginToken
}