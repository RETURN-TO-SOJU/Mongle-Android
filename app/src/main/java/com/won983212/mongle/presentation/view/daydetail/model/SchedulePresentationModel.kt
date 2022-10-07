package com.won983212.mongle.presentation.view.daydetail.model

import com.won983212.mongle.domain.model.Schedule
import com.won983212.mongle.util.DatetimeFormats


data class SchedulePresentationModel(
    val name: String,
    val sourceText: String,
    val timeRangeText: String
) {
    companion object {
        fun fromDomainModel(schedule: Schedule): SchedulePresentationModel {
            val timeRangeText =
                "${schedule.startTime.format(DatetimeFormats.TIME_12)} ~ ${
                    schedule.endTime.format(
                        DatetimeFormats.TIME_12
                    )
                }"
            return SchedulePresentationModel(schedule.name, schedule.calendar, timeRangeText)
        }
    }
}