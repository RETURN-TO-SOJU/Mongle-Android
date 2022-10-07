package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val calendarDayId: Long,
    val name: String,
    val calendar: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)