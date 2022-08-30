package com.won983212.mongle.data.di

import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.api.KakaoSendApi
import com.won983212.mongle.data.source.api.UserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
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
}