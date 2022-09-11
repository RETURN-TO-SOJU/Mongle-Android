package com.won983212.mongle.presentation.view.favorite

import android.os.Parcelable
import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.presentation.util.LocalDateParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler
import java.time.LocalDate

@Parcelize
@TypeParceler<LocalDate, LocalDateParceler>()
data class Favorite(
    val id: Int,
    val emotion: Emotion,
    val date: LocalDate,
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
