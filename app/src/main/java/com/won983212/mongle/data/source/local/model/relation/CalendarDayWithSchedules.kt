package com.won983212.mongle.data.source.local.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.won983212.mongle.data.source.local.model.CalendarDay
import com.won983212.mongle.data.source.local.model.Schedule

data class CalendarDayWithSchedules(
    @Embedded
    val day: CalendarDay,
    @Relation(parentColumn = "id", entityColumn = "calendarDayId")
    val schedules: List<Schedule>
)