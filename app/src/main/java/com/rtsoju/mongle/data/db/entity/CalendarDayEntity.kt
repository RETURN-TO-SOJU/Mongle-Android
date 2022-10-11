package com.rtsoju.mongle.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.rtsoju.mongle.domain.model.Emotion
import java.time.LocalDate

@Entity(indices = [Index(value = ["date"], unique = true)])
data class CalendarDayEntity(
    val date: LocalDate,
    val emotion: Emotion?,
    val keywords: List<String>,
    val diary: String,
    val diaryFeedback: String,
    val emotionProportions: Map<String, Int>,
    @PrimaryKey
    val id: Long = date.toString().hashCode().toLong()
)