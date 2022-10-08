package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    indices = [Index(value = ["date"])],
    foreignKeys = [
        ForeignKey(
            entity = CalendarDayEntity::class,
            parentColumns = arrayOf("date"),
            childColumns = arrayOf("date"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    val name: String,
    val calendar: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)