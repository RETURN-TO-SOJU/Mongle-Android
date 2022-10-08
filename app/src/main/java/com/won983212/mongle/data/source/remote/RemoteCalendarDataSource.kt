package com.won983212.mongle.data.source.remote

import com.won983212.mongle.data.mapper.toDomainModel
import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.api.safeApiCall
import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.data.source.remote.dto.request.DiaryRequest
import com.won983212.mongle.domain.model.CalendarDayDetail
import com.won983212.mongle.domain.model.CalendarDayPreview
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.domain.model.EmotionalSentence
import com.won983212.mongle.util.DatetimeFormats
import java.time.LocalDate
import java.time.YearMonth
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

    suspend fun updateEmotion(
        date: LocalDate,
        emotion: Emotion
    ): Result<MessageResult> {
        return safeApiCall {
            api.updateEmotion(
                date.year,
                convertDoubleDigitFormat(date.monthValue),
                convertDoubleDigitFormat(date.dayOfMonth),
                emotion.name
            )
        }
    }

    suspend fun getCalendarDayMetadata(
        startMonth: YearMonth,
        endMonth: YearMonth
    ): Result<List<CalendarDayPreview>> {
        return safeApiCall {
            api.getCalendarDayMetadata(
                startMonth.format(DatetimeFormats.MONTH_SLASH),
                endMonth.format(DatetimeFormats.MONTH_SLASH)
            ).map { it.toDomainModel() }
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
            ).toDomainModel(date)
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
            ).map { it.toDomainModel() }
        }
    }

    private fun convertDoubleDigitFormat(number: Int): String {
        return if (number < 10) {
            "0$number"
        } else {
            number.toString()
        }
    }
}