package com.won983212.mongle.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.Favorite
import java.time.LocalDate

@Entity
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val emotion: Emotion,
    val date: LocalDate,
    val title: String
)