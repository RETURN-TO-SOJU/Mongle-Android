package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.DatetimeFormats
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import java.util.*

data class Photo(
    val image: String,
    val timeText: String
) {
    companion object {
        fun fromResponse(response: CalendarDayDetail.Photo): Photo =
            Photo(
                response.url,
                response.time.format(DatetimeFormats.TIME_12.withLocale(Locale.US))
            )
    }
}