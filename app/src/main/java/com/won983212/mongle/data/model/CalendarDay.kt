package com.won983212.mongle.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.data.util.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.time.LocalDate

// TODO make calendar entity
@Entity
@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>()
data class CalendarDay(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
    val schedules: List<String>,
    @ColumnInfo(name = "emotionProportions")
    val emotionProportions: List<String>
) : Parcelable {

    override fun hashCode(): Int = id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CalendarDay

        if (id != other.id) return false
        if (date != other.date) return false
        if (emotion != other.emotion) return false
        if (keywords != other.keywords) return false
        if (diary != other.diary) return false
        if (diaryFeedback != other.diaryFeedback) return false
        if (schedules != other.schedules) return false
        if (emotionProportions != other.emotionProportions) return false

        return true
    }
}