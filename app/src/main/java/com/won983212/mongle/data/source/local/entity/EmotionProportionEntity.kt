package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.domain.model.Emotion

@Entity
data class EmotionProportionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val calendarDayId: Long,
    val emotion: Emotion,
    val percent: Int
)