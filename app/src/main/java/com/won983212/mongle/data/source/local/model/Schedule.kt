package com.won983212.mongle.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "calendarDayId")
    val calendarDayId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "calendar")
    val calendar: String,
    @ColumnInfo(name = "startTime")
    val startTime: LocalDateTime,
    @ColumnInfo(name = "endTime")
    val endTime: LocalDateTime
)