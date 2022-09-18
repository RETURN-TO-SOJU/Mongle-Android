package com.won983212.mongle.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.won983212.mongle.data.model.Favorite

@Database(entities = [Favorite::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): FavoriteDao
}