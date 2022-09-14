package com.won983212.mongle.data.di

import com.won983212.mongle.data.repository.*
import com.won983212.mongle.data.source.local.LocalFavoriteDataSource
import com.won983212.mongle.data.source.local.LocalTokenSource
import com.won983212.mongle.data.source.local.PasswordDataSource
import com.won983212.mongle.data.source.local.config.ConfigDataSource
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
        localTokenSource: LocalTokenSource,
        userDataSource: RemoteUserDataSource
    ): UserRepository {
        return UserRepositoryImpl(localTokenSource, userDataSource)
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
        kakaotalkDataSource: RemoteKakaotalkDataSource
    ): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource)
    }

    @Singleton
    @Provides
    fun provideCalendarRepository(
        remoteCalendarDataSource: RemoteCalendarDataSource
    ): CalendarRepository {
        return CalendarRepositoryImpl(
            remoteCalendarDataSource
        )
    }

    @Singleton
    @Provides
    fun provideConfigRepository(
        configDataSource: ConfigDataSource
    ): ConfigRepository {
        return ConfigRepositoryImpl(configDataSource)
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        favoriteDataSource: LocalFavoriteDataSource
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDataSource)
    }
}