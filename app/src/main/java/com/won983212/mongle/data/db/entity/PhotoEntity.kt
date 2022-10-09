package com.won983212.mongle.data.db.entity

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
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    val url: String,
    val time: LocalDateTime
)