package com.rtsoju.mongle.data.di

import android.content.Context
import com.rtsoju.mongle.data.repository.*
import com.rtsoju.mongle.data.repository.AuthRepositoryImpl
import com.rtsoju.mongle.data.repository.CalendarRepositoryImpl
import com.rtsoju.mongle.data.repository.KakaotalkRepositoryImpl
import com.rtsoju.mongle.data.repository.PasswordRepositoryImpl
import com.rtsoju.mongle.data.repository.UserRepositoryImpl
import com.rtsoju.mongle.R
import com.rtsoju.mongle.data.source.PasswordDataSource
import com.rtsoju.mongle.data.source.local.LocalCalendarDataSource
import com.rtsoju.mongle.data.source.local.LocalFavoriteDataSource
import com.rtsoju.mongle.data.source.local.LocalPasswordDataSource
import com.rtsoju.mongle.data.source.local.LocalTokenSource
import com.rtsoju.mongle.data.source.local.LocalUserDataSource
import com.rtsoju.mongle.data.source.local.config.ConfigDataSource
import com.rtsoju.mongle.data.source.remote.RemoteAuthSource
import com.rtsoju.mongle.data.source.remote.RemoteCalendarDataSource
import com.rtsoju.mongle.data.source.remote.RemoteKakaotalkDataSource
import com.rtsoju.mongle.data.source.remote.RemoteUserDataSource
import com.rtsoju.mongle.data.source.remote.dto.RemoteStatisticsDataSource
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
        kakaotalkDataSource: RemoteKakaotalkDataSource
    ): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource)
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