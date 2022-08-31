package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.RemoteCalendarDataSource
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.domain.repository.CalendarRepository
import java.time.LocalDate

internal class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource
) : CalendarRepository {

    override suspend fun updateDiary(
        date: LocalDate,
        text: String
    ): Result<MessageResult> =
        remoteCalendarDataSource.updateDiary(
            date,
            text
        )

    override suspend fun getCalendarDayMetadata(
        startMonth: LocalDate,
        endMonth: LocalDate
    ): Result<List<CalendarDay>> =
        remoteCalendarDataSource.getCalendarDayMetadata(
            startMonth,
            endMonth
        )

    override suspend fun getCalendarDayDetail(date: LocalDate): Result<CalendarDayDetail> =
        remoteCalendarDataSource.getCalendarDayDetail(date)

    override suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): Result<List<EmotionalSentence>> =
        remoteCalendarDataSource.getDayEmotionalSentences(
            date,
            emotion
        )
}