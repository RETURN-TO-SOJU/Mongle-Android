package com.won983212.mongle.data.di

import com.won983212.mongle.data.local.source.PasswordDataSource
import com.won983212.mongle.data.local.source.TokenDataSource
import com.won983212.mongle.data.remote.source.RemoteCalendarDataSource
import com.won983212.mongle.data.remote.source.RemoteKakaotalkDataSource
import com.won983212.mongle.data.remote.source.RemoteUserDataSource
import com.won983212.mongle.data.repository.CalendarRepositoryImpl
import com.won983212.mongle.data.repository.KakaotalkRepositoryImpl
import com.won983212.mongle.data.repository.PasswordRepositoryImpl
import com.won983212.mongle.data.repository.UserRepositoryImpl
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.domain.repository.KakaotalkRepository
import com.won983212.mongle.domain.repository.PasswordRepository
import com.won983212.mongle.domain.repository.UserRepository
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
    fun provideKakaotalkRepository(kakaotalkDataSource: RemoteKakaotalkDataSource): KakaotalkRepository {
        return KakaotalkRepositoryImpl(kakaotalkDataSource)
    }

    @Singleton
    @Provides
    fun provideCalendarRepository(calendarDataSource: RemoteCalendarDataSource): CalendarRepository {
        return CalendarRepositoryImpl(calendarDataSource)
    }
}