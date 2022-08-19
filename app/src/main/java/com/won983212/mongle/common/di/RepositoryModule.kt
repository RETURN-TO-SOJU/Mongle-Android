package com.won983212.mongle.common.di

import android.content.Context
import com.won983212.mongle.data.repository.FilePasswordRepository
import com.won983212.mongle.data.repository.FileTokenRepository
import com.won983212.mongle.repository.PasswordRepository
import com.won983212.mongle.repository.TokenRepository
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
    fun providePasswordRepository(@ApplicationContext context: Context): PasswordRepository {
        return FilePasswordRepository(context)
    }

    @Singleton
    @Provides
    fun provideTokenRepository(@ApplicationContext context: Context): TokenRepository {
        return FileTokenRepository(context)
    }
}