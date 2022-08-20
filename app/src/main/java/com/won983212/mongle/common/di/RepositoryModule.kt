package com.won983212.mongle.common.di

import com.won983212.mongle.data.local.source.PasswordDataSource
import com.won983212.mongle.data.local.source.TokenDataSource
import com.won983212.mongle.data.remote.source.CalendarDataSource
import com.won983212.mongle.data.remote.source.KakaotalkDataSource
import com.won983212.mongle.data.remote.source.LoginDataSource
import com.won983212.mongle.data.repository.AuthRepositoryImpl
import com.won983212.mongle.data.repository.CalendarRepositoryImpl
import com.won983212.mongle.data.repository.KakaotalkRepositoryImpl
import com.won983212.mongle.data.repository.PasswordRepositoryImpl
import com.won983212.mongle.repository.AuthRepository
import com.won983212.mongle.repository.CalendarRepository
import com.won983212.mongle.repository.KakaotalkRepository
import com.won983212.mongle.repository.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Singleton
    @Provides
    fun providePasswordRepository(passwordDataSource: PasswordDataSource): PasswordRepository {
        return PasswordRepositoryImpl(passwordDataSource)
    }

    @Singleton
    @Provides
    fun provideTokenRepository(
        tokenDataSource: TokenDataSource,
        loginDataSource: LoginDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(tokenDataSource, loginDataSource)
    }

    @Singleton
    @Provides
    fun provideKakaotalkRepository(kakaotalkDataSource: KakaotalkDataSource): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource)
    }

    @Singleton
    @Provides
    fun provideCalendarRepository(calendarDataSource: CalendarDataSource): CalendarRepository {
        return CalendarRepositoryImpl(calendarDataSource)
    }
}