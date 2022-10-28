package com.rtsoju.mongle.data.di

import android.content.Context
import com.rtsoju.mongle.R
import com.rtsoju.mongle.data.repository.*
import com.rtsoju.mongle.data.source.PasswordDataSource
import com.rtsoju.mongle.data.source.local.*
import com.rtsoju.mongle.data.source.local.config.ConfigDataSource
import com.rtsoju.mongle.data.source.remote.*
import com.rtsoju.mongle.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Singleton
    @Provides
    fun providePasswordRepository(passwordDataSource: LocalPasswordDataSource): PasswordRepository {
        return PasswordRepositoryImpl(passwordDataSource)
    }

    @Singleton
    @Provides
    fun providePasswordDataSource(
        @ApplicationContext context: Context
    ): PasswordDataSource {
        return LocalPasswordDataSource(context)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        localTokenSource: LocalTokenSource,
        localUserDataSource: LocalUserDataSource,
        remoteUserDataSource: RemoteUserDataSource
    ): UserRepository {
        return UserRepositoryImpl(localTokenSource, localUserDataSource, remoteUserDataSource)
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
        passwordDataSource: LocalPasswordDataSource
    ): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource, passwordDataSource)
    }

    @Singleton
    @Provides
    fun provideCalendarRepository(
        remoteCalendarDataSource: RemoteCalendarDataSource,
        localCalendarDataSource: LocalCalendarDataSource
    ): CalendarRepository {
        return CalendarRepositoryImpl(
            localCalendarDataSource,
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
    fun provideConfigDataSource(
        @ApplicationContext context: Context
    ): ConfigDataSource {
        return ConfigDataSource(
            context,
            ConfigDataSource.ResourceContext(
                R.xml.settings,
                R.styleable.settings,
                R.styleable.settings_name,
                R.styleable.settings_defaultValue
            )
        )
    }

    @Singleton
    @Provides
    fun provideFavoriteRepository(
        favoriteDataSource: LocalFavoriteDataSource
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteDataSource)
    }

    @Singleton
    @Provides
    fun provideStatisticsRepository(
        statisticsDataSource: RemoteStatisticsDataSource
    ): StatisticsRepository {
        return StatisticsRepositoryImpl(statisticsDataSource)
    }
}