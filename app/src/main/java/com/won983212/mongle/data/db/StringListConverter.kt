package com.won983212.mongle.data.db

import androidx.room.TypeConverter
import com.won983212.mongle.util.parseLocalDateTime
import com.won983212.mongle.util.toEpochMilli
import java.time.LocalDate
import java.time.LocalDateTime

class StringListConverter {
    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.split(",")
    }

    @TypeConverter
    fun toString(datetime: List<String>?): String? {
        return datetime?.joinToString()
    }
}