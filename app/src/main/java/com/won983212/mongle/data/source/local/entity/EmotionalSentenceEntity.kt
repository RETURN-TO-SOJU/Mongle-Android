package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.domain.model.Emotion
import java.time.LocalDate

@Entity
data class EmotionalSentenceEntity(
    @PrimaryKey
    val id: Long,
    val date: LocalDate,
    val sentence: String,
    val emotion: Emotion
)
