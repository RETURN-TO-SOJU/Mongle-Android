package com.won983212.mongle.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.won983212.mongle.data.util.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.time.LocalDate

@Entity
@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>()
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "emotion")
    val emotion: Emotion,
    @ColumnInfo(name = "date")
    val date: LocalDate,
    @ColumnInfo(name = "title")
    val title: String
) : Parcelable {
    override fun hashCode(): Int = id

    override fun equals(other: Any?): Boolean {
        if (other !is Favorite) {
            return false
        }
        return other.id == id
    }
}