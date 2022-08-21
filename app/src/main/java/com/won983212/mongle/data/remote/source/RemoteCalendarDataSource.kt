package com.won983212.mongle.data.remote.source

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.remote.api.CalendarApi
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.safeApiCall
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.request.DiaryRequest
import com.won983212.mongle.data.remote.model.response.CalendarDay
import com.won983212.mongle.data.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.remote.model.response.EmotionalSentence
import com.won983212.mongle.domain.repository.UserRepository
import java.time.LocalDate
import javax.inject.Inject

class RemoteCalendarDataSource @Inject constructor(
    private val api: CalendarApi
) {
    suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate,
        text: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.updateDiary(
                accessToken,
                date.year, date.monthValue, date.dayOfMonth,
                DiaryRequest(text)
            )
        }
    }

    suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        accessToken: String,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>? {
        return safeApiCall(callback) {
            api.getCalendarDayMetadata(
                accessToken,
                startMonth.year, startMonth.monthValue, endMonth.monthValue
            )
        }
    }

    suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate
    ): CalendarDayDetail? {
        return safeApiCall(callback) {
            api.getCalendarDayDetail(
                accessToken,
                date.year, date.monthValue, date.dayOfMonth
            )
        }
    }

    suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        accessToken: String,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>? {
        return safeApiCall(callback) {
            api.getDayEmotionalSentences(
                accessToken,
                date.year, date.monthValue, date.dayOfMonth,
                emotion.name
            )
        }
    }
}