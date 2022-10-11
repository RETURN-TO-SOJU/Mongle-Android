package com.rtsoju.mongle.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rtsoju.mongle.data.db.converter.IntValueMapConverter
import com.rtsoju.mongle.data.db.converter.LocalDateTimeConverter
import com.rtsoju.mongle.data.db.converter.StringListConverter
import com.rtsoju.mongle.data.db.dao.*
import com.rtsoju.mongle.data.db.entity.*

@Database(
    entities = [
        FavoriteEntity::class,
        CalendarDayEntity::class,
        EmotionalSentenceEntity::class,
        ScheduleEntity::class,
        PhotoEntity::class
    ],
    version = 3
)
@TypeConverters(
    LocalDateTimeConverter::class,
    StringListConverter::class,
    IntValueMapConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun calenderDao(): CalendarDao
    abstract fun emotionalSentencesDao(): EmotionalSentenceDao
    abstract fun calendarPhotoDao(): CalendarPhotoDao
    abstract fun calenderScheduleDao(): CalendarScheduleDao
}