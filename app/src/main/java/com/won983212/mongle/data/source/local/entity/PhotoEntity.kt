package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val calendarDayId: Long,
    val url: String,
    val time: LocalDateTime
)