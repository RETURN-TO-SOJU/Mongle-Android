package com.won983212.mongle.data.source.local

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import java.time.LocalDate
import java.time.YearMonth

internal class LocalCalendarDataSource {

    suspend fun updateDiary(
        date: LocalDate,
        text: String
    ) {

    }

    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    ) {

    }

    suspend fun getCalendarDayMetadata(
        startMonth: YearMonth,
        endMonth: YearMonth
    ): Result<List<CalendarDay>> {

    }

    suspend fun getCalendarDayDetail(
        date: LocalDate
    ): Result<CalendarDayDetail> {

    }

    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): Result<List<EmotionalSentence>> {

    }
}