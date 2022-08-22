package com.won983212.mongle.data.di

import com.won983212.mongle.data.local.source.ConfigDataSource
import com.won983212.mongle.data.local.source.PasswordDataSource
import com.won983212.mongle.data.local.source.TokenDataSource
import com.won983212.mongle.data.remote.source.RemoteCalendarDataSource
import com.won983212.mongle.data.remote.source.RemoteKakaotalkDataSource
import com.won983212.mongle.data.remote.source.RemoteUserDataSource
import com.won983212.mongle.data.repository.*
import com.won983212.mongle.domain.repository.*
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
        userDataSource: RemoteUserDataSource
    ): UserRepository {
        return UserRepositoryImpl(tokenDataSource, userDataSource)
    }

    @Singleton
    @Provides
    fun provideKakaotalkRepository(
        kakaotalkDataSource: RemoteKakaotalkDataSource,
        userRepository: UserRepository
    ): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource, userRepository)
    }

    @Singleton
    @Provides
    fun provideCalendarRepository(
        calendarDataSource: RemoteCalendarDataSource,
        userRepository: UserRepository
    ): CalendarRepository {
        return CalendarRepositoryImpl(calendarDataSource, userRepository)
    }

    @Singleton
    @Provides
    fun provideConfigRepository(
        configDataSource: ConfigDataSource
    ): ConfigRepository {
        return ConfigRepositoryImpl(configDataSource)
    }
}