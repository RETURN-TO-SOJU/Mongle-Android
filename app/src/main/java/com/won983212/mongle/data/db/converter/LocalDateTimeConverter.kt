package com.won983212.mongle.data.db.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateTimeConverter {
    @TypeConverter
    fun toLocalDateTime(str: String?): LocalDateTime? {
        return if (str == null) {
            null
        } else {
            LocalDateTime.parse(str)
        }
    }

    @TypeConverter
    fun toLocalDateTimeString(datetime: LocalDateTime?): String? {
        return datetime?.toString()
    }

    @TypeConverter
    fun toLocalDate(str: String?): LocalDate? {
        return if (str == null) {
            null
        } else {
            LocalDate.parse(str)
        }
    }

    @TypeConverter
    fun toLocalDateString(date: LocalDate?): String? {
        return date?.toString()
    }
}