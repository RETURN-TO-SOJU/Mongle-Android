package com.won983212.mongle.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.won983212.mongle.data.source.local.entity.*

@Database(
    entities = [
        FavoriteEntity::class,
        CalendarDayEntity::class,
        EmotionalSentenceEntity::class,
        ScheduleEntity::class,
        PhotoEntity::class,
        EmotionProportionEntity::class
    ],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun calenderDao(): CalendarDao
    abstract fun emotionalSentencesDao(): EmotionalSentenceDao
    abstract fun calendarPhotoDao(): CalendarPhotoDao
    abstract fun calenderScheduleDao(): CalendarScheduleDao
    abstract fun calendarEmotionProportionDao(): CalendarEmotionProportionDao
}