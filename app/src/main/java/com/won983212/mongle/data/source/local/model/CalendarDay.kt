package com.won983212.mongle.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.won983212.mongle.data.model.Emotion
import java.time.LocalDate

// TODO make calendar entity
@Entity
data class CalendarDay(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "emotion")
    val emotion: Emotion,
    @ColumnInfo(name = "subjects")
    val keywords: List<String>,
    @ColumnInfo(name = "diary")
    val diary: String,
    @ColumnInfo(name = "diaryFeedback")
    val diaryFeedback: String,
    @ColumnInfo(name = "schedules")
    val schedules: List<Schedule>,
    @ColumnInfo(name = "emotionProportions")
    val emotionProportions: List<EmotionalSentence>
)

data class CalendarDayPreview(
    @SerializedName("date")
    val date: LocalDate,
    @SerializedName("emotion")
    val emotion: Emotion,
    @SerializedName("subjectList")
    val subjectList: List<String>
)