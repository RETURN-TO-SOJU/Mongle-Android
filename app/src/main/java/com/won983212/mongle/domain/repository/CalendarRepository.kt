package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.domain.model.*
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

    suspend fun getCalendarDayPreview(
        startMonth: YearMonth,
        endMonth: YearMonth,
        cachePolicy: CachePolicy
    ): Result<List<CalendarDayPreview>>

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