package com.won983212.mongle.data.db

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.split(",")
    }

    @TypeConverter
    fun toString(datetime: List<String>?): String? {
        return datetime?.joinToString(separator = ",")
    }
}