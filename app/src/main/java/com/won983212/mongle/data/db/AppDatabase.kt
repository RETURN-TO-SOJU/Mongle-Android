package com.won983212.mongle.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.won983212.mongle.data.source.local.model.CalendarDay
import com.won983212.mongle.data.source.local.model.EmotionalSentence
import com.won983212.mongle.data.source.local.model.Favorite
import com.won983212.mongle.data.source.local.model.Schedule

// TODO Model local, remote, common 잘 분리하기
@Database(
    entities = [Favorite::class, CalendarDay::class, EmotionalSentence::class, Schedule::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun calenderDao(): CalendarDao
}