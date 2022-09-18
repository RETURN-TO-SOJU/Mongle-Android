package com.won983212.mongle.data.db

import androidx.room.TypeConverter
import com.won983212.mongle.util.parseLocalDateTime
import com.won983212.mongle.util.toEpochMilli
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun toLocalDateTime(data: Long?): LocalDateTime? {
        return if (data == null) {
            null
        } else {
            parseLocalDateTime(data)
        }
    }

    @TypeConverter
    fun toLocalDateTimeString(datetime: LocalDateTime?): Long? {
        return datetime?.toEpochMilli()
    }

    @TypeConverter
    fun toLocalDate(data: Long?): LocalDate? {
        return if (data == null) {
            null
        } else {
            LocalDate.ofEpochDay(data)
        }
    }

    @TypeConverter
    fun toLocalDateString(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}