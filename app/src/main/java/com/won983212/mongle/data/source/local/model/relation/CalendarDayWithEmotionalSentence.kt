package com.won983212.mongle.data.source.local.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.won983212.mongle.data.source.local.model.CalendarDay
import com.won983212.mongle.data.source.local.model.EmotionalSentence

class CalendarDayWithEmotionalSentence(
    @Embedded
    val day: CalendarDay,
    @Relation(parentColumn = "id", entityColumn = "calendarDayId")
    val sentences: List<EmotionalSentence>
)