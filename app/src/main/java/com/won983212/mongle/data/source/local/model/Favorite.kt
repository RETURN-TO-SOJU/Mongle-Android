package com.won983212.mongle.data.source.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.util.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.time.LocalDate

@Entity
@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>()
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "emotion")
    val emotion: Emotion,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "title")
    val title: String
) : Parcelable {
    override fun hashCode(): Int = id.toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Favorite

        if (id != other.id) return false
        if (emotion != other.emotion) return false
        if (date != other.date) return false
        if (title != other.title) return false

        return true
    }
}