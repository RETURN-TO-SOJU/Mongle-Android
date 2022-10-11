package com.rtsoju.mongle.data.db.converter

import androidx.room.TypeConverter

class IntValueMapConverter {
    @TypeConverter
    fun toIntValueMap(data: String?): Map<String, Int>? {
        return data?.split(",")?.associate {
            val (left, right) = it.split("=")
            left to right.toInt()
        }
    }

    @TypeConverter
    fun toString(data: Map<String, Int>?): String? {
        return data?.entries?.joinToString(separator = ",") {
            "${it.key}=${it.value}"
        }
    }
}