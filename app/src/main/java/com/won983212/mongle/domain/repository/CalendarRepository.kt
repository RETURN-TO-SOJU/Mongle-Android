package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.data.util.CachePolicy
import java.time.LocalDate
import java.time.YearMonth

interface CalendarRepository {

    suspend fun updateDiary(
        date: LocalDate,
        text: String
    ): Result<MessageResult>

    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    ): Result<MessageResult>

    suspend fun getCalendarDayMetadata(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy
    ): Result<List<CalendarDay>>

    suspend fun getCalendarDayDetail(
        date: LocalDate,
        cachePolicy: CachePolicy
    ): Result<CalendarDayDetail>

    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion,
        cachePolicy: CachePolicy
    ): Result<List<EmotionalSentence>>
}