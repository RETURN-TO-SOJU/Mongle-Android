package com.won983212.mongle.hlit

import com.won983212.mongle.data.PasswordRepositoryImpl
import com.won983212.mongle.repository.PasswordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal abstract class PasswordRepositoryModule {

    @Binds
    abstract fun bindPasswordRepository(
        impl: PasswordRepositoryImpl
    ): PasswordRepository
}