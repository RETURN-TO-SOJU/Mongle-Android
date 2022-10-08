package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Entity(indices = [Index(value = ["date"], unique = true)])
data class CalendarDayEntity(
    val date: LocalDate,
    val emotion: Emotion?,
    val keywords: List<String>,
    val diary: String,
    val diaryFeedback: String,
    @PrimaryKey
    val id: Long = date.toString().hashCode().toLong()
)