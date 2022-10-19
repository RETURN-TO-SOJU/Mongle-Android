package com.rtsoju.mongle.data.di

import com.rtsoju.mongle.domain.usecase.auth.GetAccessTokenUseCase
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

annotation class NoAuthorization

class AuthenticationInterceptor @Inject constructor(
    private val getAccessToken: Lazy<GetAccessTokenUseCase>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val noAuthAnnotation = request.tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(NoAuthorization::class.java)

        if (noAuthAnnotation == null) {
            val token = runBlocking { getAccessToken.get().invoke() }
            token.onSuccess {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
            }
        }

        return chain.proceed(request)
    }
}