package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Entity
data class CalendarDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    val emotion: Emotion,
    val keywords: List<String>,
    val diary: String,
    val diaryFeedback: String
)