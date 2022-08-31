package com.won983212.mongle.domain.di

import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.domain.usecase.ValidateTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateTokenUseCase(
        userRepository: UserRepository,
        authRepository: AuthRepository
    ): ValidateTokenUseCase {
        return ValidateTokenUseCase(userRepository, authRepository)
    }
}