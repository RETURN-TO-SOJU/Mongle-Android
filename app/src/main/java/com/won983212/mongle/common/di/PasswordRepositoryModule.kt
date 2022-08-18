package com.won983212.mongle.common.di

import android.content.Context
import com.won983212.mongle.data.PasswordRepositoryImpl
import com.won983212.mongle.repository.PasswordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class PasswordRepositoryModule {

    @Singleton
    @Provides
    fun providePasswordRepository(@ApplicationContext context: Context): PasswordRepository {
        return PasswordRepositoryImpl(context)
    }
}