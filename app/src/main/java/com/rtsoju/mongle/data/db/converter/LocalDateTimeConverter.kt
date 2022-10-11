package com.rtsoju.mongle.data.db.converter

import androidx.room.TypeConverter
import com.rtsoju.mongle.util.parseLocalDateTime
import com.rtsoju.mongle.util.toEpochMilli
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