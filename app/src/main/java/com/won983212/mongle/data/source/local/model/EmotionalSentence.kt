package com.won983212.mongle.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.won983212.mongle.data.model.Emotion

data class EmotionalSentence(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "calendarDayId")
    val calendarDayId: Long,
    @ColumnInfo(name = "sentence")
    val sentence: String,
    @ColumnInfo(name = "emotion")
    val emotion: Emotion
)
