package com.won983212.mongle.data.repository

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.remote.RemoteCalendarDataSource
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.domain.repository.AuthRepository
import com.won983212.mongle.domain.repository.CalendarRepository
import com.won983212.mongle.domain.repository.UserRepository
import java.time.LocalDate

internal class CalendarRepositoryImpl(
    private val remoteCalendarDataSource: RemoteCalendarDataSource,
    private val authRepository: AuthRepository
) : CalendarRepository {

    override suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        text: String
    ): MessageResult? =
        remoteCalendarDataSource.updateDiary(
            callback,
            authRepository.getAccessToken(),
            date,
            text
        )

    override suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>? =
        remoteCalendarDataSource.getCalendarDayMetadata(
            callback,
            authRepository.getAccessToken(),
            startMonth,
            endMonth
        )

    override suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        date: LocalDate
    ): CalendarDayDetail? =
        remoteCalendarDataSource.getCalendarDayDetail(
            callback,
            authRepository.getAccessToken(),
            date
        )

    override suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>? =
        remoteCalendarDataSource.getDayEmotionalSentences(
            callback,
            authRepository.getAccessToken(),
            date,
            emotion
        )
}