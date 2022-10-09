package com.won983212.mongle.data.db.converter

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.split(",")
    }

    @TypeConverter
    fun toString(data: List<String>?): String? {
        return data?.joinToString(separator = ",")
    }
}