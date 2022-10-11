package com.rtsoju.mongle.data.di

import com.rtsoju.mongle.BuildConfig
import com.rtsoju.mongle.data.source.api.AuthApi
import com.rtsoju.mongle.data.source.api.CalendarApi
import com.rtsoju.mongle.data.source.api.KakaoSendApi
import com.rtsoju.mongle.data.source.api.UserApi
import com.rtsoju.mongle.debug.mock.MockAuthApi
import com.rtsoju.mongle.debug.mock.MockCalendarApi
import com.rtsoju.mongle.debug.mock.MockKakaoSendApi
import com.rtsoju.mongle.debug.mock.MockUserApi
import com.rtsoju.mongle.domain.repository.AuthRepository
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
    fun provideLoginApi(authRepository: AuthRepository, retrofit: Retrofit): UserApi {
        if (BuildConfig.USE_MOCKING) {
            return MockUserApi(authRepository)
        }
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideKakaoSendApi(authRepository: AuthRepository, retrofit: Retrofit): KakaoSendApi {
        if (BuildConfig.USE_MOCKING) {
            return MockKakaoSendApi(authRepository)
        }
        return retrofit.create(KakaoSendApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCalendarApi(authRepository: AuthRepository, retrofit: Retrofit): CalendarApi {
        if (BuildConfig.USE_MOCKING) {
            return MockCalendarApi(authRepository)
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