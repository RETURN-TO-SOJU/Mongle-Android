package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.DatetimeFormats
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail


data class Schedule(
    val name: String,
    val sourceText: String,
    val timeRangeText: String
) {
    companion object {
        fun fromResponse(schedule: CalendarDayDetail.Schedule): Schedule {
            val timeRangeText =
                "${schedule.startTime.format(DatetimeFormats.TIME_12)} ~ ${
                    schedule.endTime.format(
                        DatetimeFormats.TIME_12
                    )
                }"
            return Schedule(schedule.name, schedule.calendar, timeRangeText)
        }
    }
}