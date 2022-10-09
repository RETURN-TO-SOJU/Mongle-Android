package com.won983212.mongle.data.db.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.won983212.mongle.data.db.entity.CalendarDayEntity
import com.won983212.mongle.data.db.entity.PhotoEntity
import com.won983212.mongle.data.db.entity.ScheduleEntity

data class CalendarDayWithDetails(
    @Embedded
    val day: CalendarDayEntity,
    @Relation(parentColumn = "date", entityColumn = "date")
    val photos: List<PhotoEntity>,
    @Relation(parentColumn = "date", entityColumn = "date")
    val schedules: List<ScheduleEntity>
)