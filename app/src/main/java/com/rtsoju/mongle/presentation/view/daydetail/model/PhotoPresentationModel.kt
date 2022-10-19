package com.rtsoju.mongle.presentation.view.daydetail.model

import com.rtsoju.mongle.domain.model.Photo
import com.rtsoju.mongle.util.DatetimeFormats
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