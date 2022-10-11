package com.rtsoju.mongle.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rtsoju.mongle.domain.model.Emotion
import java.time.LocalDate

@Entity
data class EmotionalSentenceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    val sentence: String,
    val emotion: Emotion
)
