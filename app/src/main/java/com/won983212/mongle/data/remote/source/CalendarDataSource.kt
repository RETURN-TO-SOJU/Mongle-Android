package com.won983212.mongle.data.remote.source

import com.won983212.mongle.common.Emotion
import com.won983212.mongle.data.remote.api.CalendarApi
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.data.remote.api.safeApiCall
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.request.DiaryRequest
import com.won983212.mongle.data.remote.model.response.CalendarDay
import com.won983212.mongle.data.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.remote.model.response.EmotionalSentence
import com.won983212.mongle.repository.AuthRepository
import java.time.LocalDate
import javax.inject.Inject

class CalendarDataSource @Inject constructor(
    private val api: CalendarApi,
    private val authRepository: AuthRepository
) {
    suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        text: String
    ): MessageResult? {
        return safeApiCall(callback) {
            val token = authRepository.getCurrentToken().accessToken
            api.updateDiary(
                token,
                date.year, date.monthValue, date.dayOfMonth,
                DiaryRequest(text)
            )
        }
    }

    suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>? {
        return safeApiCall(callback) {
            val token = authRepository.getCurrentToken().accessToken
            api.getCalendarDayMetadata(
                token,
                startMonth.year, startMonth.monthValue, endMonth.monthValue
            )
        }
    }

    suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        date: LocalDate
    ): CalendarDayDetail? {
        return safeApiCall(callback) {
            val token = authRepository.getCurrentToken().accessToken
            api.getCalendarDayDetail(
                token,
                date.year, date.monthValue, date.dayOfMonth
            )
        }
    }

    suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>? {
        return safeApiCall(callback) {
            val token = authRepository.getCurrentToken().accessToken
            api.getDayEmotionalSentences(
                token,
                date.year, date.monthValue, date.dayOfMonth,
                emotion.name
            )
        }
    }
}