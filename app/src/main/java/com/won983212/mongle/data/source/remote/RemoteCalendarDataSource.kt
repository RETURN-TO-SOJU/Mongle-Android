package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.CalendarDataSource
import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.api.RequestLifecycleCallback
import com.won983212.mongle.data.source.api.safeApiCall
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.DiaryRequest
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class RemoteCalendarDataSource @Inject constructor(
    private val api: CalendarApi
) : CalendarDataSource {

    override suspend fun updateDiary(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        text: String
    ): MessageResult? {
        return safeApiCall(callback) {
            api.updateDiary(
                date.year,
                convertDoubleDigitFormat(date.monthValue),
                convertDoubleDigitFormat(date.dayOfMonth),
                DiaryRequest(text)
            )
        }
    }

    override suspend fun getCalendarDayMetadata(
        callback: RequestLifecycleCallback,
        startMonth: LocalDate,
        endMonth: LocalDate
    ): List<CalendarDay>? {
        return safeApiCall(callback) {
            api.getCalendarDayMetadata(
                startMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")),
                endMonth.format(
                    DateTimeFormatter.ofPattern("yyyy-MM")
                )
            )
        }
    }

    override suspend fun getCalendarDayDetail(
        callback: RequestLifecycleCallback,
        date: LocalDate
    ): CalendarDayDetail? {
        return safeApiCall(callback) {
            api.getCalendarDayDetail(
                date.year,
                convertDoubleDigitFormat(date.monthValue),
                convertDoubleDigitFormat(date.dayOfMonth)
            )
        }
    }

    override suspend fun getDayEmotionalSentences(
        callback: RequestLifecycleCallback,
        date: LocalDate,
        emotion: Emotion
    ): List<EmotionalSentence>? {
        return safeApiCall(callback) {
            api.getDayEmotionalSentences(
                date.year,
                convertDoubleDigitFormat(date.monthValue),
                convertDoubleDigitFormat(date.dayOfMonth),
                emotion.name
            )
        }
    }

    private fun convertDoubleDigitFormat(number: Int): String {
        if (number < 10) {
            return "0$number"
        } else {
            return number.toString()
        }
    }
}