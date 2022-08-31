package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.api.CalendarApi
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
) {

    suspend fun updateDiary(
        date: LocalDate,
        text: String
    ): Result<MessageResult> {
        return safeApiCall {
            api.updateDiary(
                date.year,
                convertDoubleDigitFormat(date.monthValue),
                convertDoubleDigitFormat(date.dayOfMonth),
                DiaryRequest(text)
            )
        }
    }

    suspend fun getCalendarDayMetadata(
        startMonth: LocalDate,
        endMonth: LocalDate
    ): Result<List<CalendarDay>> {
        return safeApiCall {
            api.getCalendarDayMetadata(
                startMonth.format(DateTimeFormatter.ofPattern("yyyy-MM")),
                endMonth.format(
                    DateTimeFormatter.ofPattern("yyyy-MM")
                )
            )
        }
    }

    suspend fun getCalendarDayDetail(
        date: LocalDate
    ): Result<CalendarDayDetail> {
        return safeApiCall {
            api.getCalendarDayDetail(
                date.year,
                convertDoubleDigitFormat(date.monthValue),
                convertDoubleDigitFormat(date.dayOfMonth)
            )
        }
    }

    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): Result<List<EmotionalSentence>> {
        return safeApiCall {
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