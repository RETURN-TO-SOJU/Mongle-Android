package com.won983212.mongle.data.source.local.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.won983212.mongle.data.source.local.entity.CalendarDayEntity
import com.won983212.mongle.data.source.local.entity.EmotionProportionEntity
import com.won983212.mongle.data.source.local.entity.PhotoEntity
import com.won983212.mongle.data.source.local.entity.ScheduleEntity

data class CalendarDayWithDetails(
    @Embedded
    val day: CalendarDayEntity,
    @Relation(parentColumn = "date", entityColumn = "date")
    val photos: List<PhotoEntity>,
    @Relation(parentColumn = "date", entityColumn = "date")
    val schedules: List<ScheduleEntity>,
    @Relation(parentColumn = "date", entityColumn = "date")
    val emotionProportions: List<EmotionProportionEntity>
)