package com.rtsoju.mongle.domain.model

import android.os.Parcelable
import com.rtsoju.mongle.data.util.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.time.LocalDate

@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>()
data class Favorite(
    val id: Long,
    val emotion: Emotion,
    val date: LocalDate,
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