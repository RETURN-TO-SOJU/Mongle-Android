package com.rtsoju.mongle.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rtsoju.mongle.domain.model.Emotion
import java.time.LocalDate

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val emotion: Emotion,
    val date: LocalDate,
    val title: String
)