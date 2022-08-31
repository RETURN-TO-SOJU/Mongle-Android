package com.won983212.mongle.data.di

import com.won983212.mongle.domain.repository.AuthRepository
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

annotation class NoAuthorization

class AuthenticationInterceptor @Inject constructor(
    private val authRepository: Lazy<AuthRepository>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val noAuthAnnotation = request.tag(Invocation::class.java)
            ?.method()
            ?.getAnnotation(NoAuthorization::class.java)

        if (noAuthAnnotation == null) {
            val token = runBlocking { authRepository.get().getAccessToken() }
            token.onSuccess {
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $it")
                    .build()
            }
        }

        return chain.proceed(request)
    }
}