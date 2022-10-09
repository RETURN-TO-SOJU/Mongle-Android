package com.won983212.mongle.data.di

import android.content.Context
import androidx.room.Room
import com.won983212.mongle.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DBModule {

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mongle-database"
        ).fallbackToDestructiveMigration().build()
    }
}