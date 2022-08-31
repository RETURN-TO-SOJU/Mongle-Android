package com.won983212.mongle.data.di

import com.won983212.mongle.BuildConfig
import com.won983212.mongle.data.source.api.AuthApi
import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.api.KakaoSendApi
import com.won983212.mongle.data.source.api.UserApi
import com.won983212.mongle.debug.mock.MockAuthApi
import com.won983212.mongle.debug.mock.MockCalendarApi
import com.won983212.mongle.debug.mock.MockKakaoSendApi
import com.won983212.mongle.debug.mock.MockUserApi
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
        if (BuildConfig.USE_MOCKING) {
            return MockUserApi()
        }
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoSendApi(retrofit: Retrofit): KakaoSendApi {
        if (BuildConfig.USE_MOCKING) {
            return MockKakaoSendApi()
        }
        return retrofit.create(KakaoSendApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCalendarApi(retrofit: Retrofit): CalendarApi {
        if (BuildConfig.USE_MOCKING) {
            return MockCalendarApi()
        }
        return retrofit.create(CalendarApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        if (BuildConfig.USE_MOCKING) {
            return MockAuthApi()
        }
        return retrofit.create(AuthApi::class.java)
    }
}