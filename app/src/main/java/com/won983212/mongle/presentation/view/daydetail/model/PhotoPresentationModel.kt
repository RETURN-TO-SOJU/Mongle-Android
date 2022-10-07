package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.domain.model.Photo
import com.won983212.mongle.util.DatetimeFormats
import java.util.*

data class PhotoPresentationModel(
    val image: String,
    val timeText: String
) {
    companion object {
        fun fromDomainModel(response: Photo): PhotoPresentationModel =
            PhotoPresentationModel(
                response.url,
                response.time.format(DatetimeFormats.TIME_12.withLocale(Locale.US))
            )
    }
}