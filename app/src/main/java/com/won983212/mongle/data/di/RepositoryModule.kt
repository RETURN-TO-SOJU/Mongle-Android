package com.won983212.mongle.data.di

import com.won983212.mongle.data.repository.*
import com.won983212.mongle.data.source.local.ConfigDataSource
import com.won983212.mongle.data.source.local.LocalTokenSource
import com.won983212.mongle.data.source.local.PasswordDataSource
import com.won983212.mongle.data.source.remote.RemoteAuthSource
import com.won983212.mongle.data.source.remote.RemoteCalendarDataSource
import com.won983212.mongle.data.source.remote.RemoteKakaotalkDataSource
import com.won983212.mongle.data.source.remote.RemoteUserDataSource
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
    fun provideUserRepository(
        authRepository: AuthRepository,
        userDataSource: RemoteUserDataSource
    ): UserRepository {
        return UserRepositoryImpl(authRepository, userDataSource)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        localTokenSource: LocalTokenSource,
        remoteAuthSource: RemoteAuthSource
    ): AuthRepository {
        return AuthRepositoryImpl(localTokenSource, remoteAuthSource)
    }

    @Singleton
    @Provides
    fun provideKakaotalkRepository(
        kakaotalkDataSource: RemoteKakaotalkDataSource,
        authRepository: AuthRepository
    ): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource, authRepository)
    }

    @Singleton
    @Provides
    fun provideCalendarRepository(
        remoteCalendarDataSource: RemoteCalendarDataSource,
        authRepository: AuthRepository
    ): CalendarRepository {
        return CalendarRepositoryImpl(
            remoteCalendarDataSource,
            authRepository
        )
    }

    @Singleton
    @Provides
    fun provideConfigRepository(
        configDataSource: ConfigDataSource
    ): ConfigRepository {
        return ConfigRepositoryImpl(configDataSource)
    }
}