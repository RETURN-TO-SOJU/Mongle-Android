package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

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
data class EmotionProportionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    val emotion: Emotion,
    val percent: Int
)