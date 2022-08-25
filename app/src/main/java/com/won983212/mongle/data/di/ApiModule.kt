package com.won983212.mongle.data.di

import com.won983212.mongle.BuildConfig
import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.api.KakaoSendApi
import com.won983212.mongle.data.source.api.TestApi
import com.won983212.mongle.data.source.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApiModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoSendApi(retrofit: Retrofit): KakaoSendApi {
        return retrofit.create(KakaoSendApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCalendarApi(retrofit: Retrofit): CalendarApi {
        return retrofit.create(CalendarApi::class.java)
    }

    // TODO For test. Remove it after 중간발표
    @Singleton
    @Provides
    fun provideTestApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): TestApi {
        return Retrofit.Builder()
            .baseUrl("http://3.39.89.73:5000/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(TestApi::class.java)
    }
}