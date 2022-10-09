package com.won983212.mongle.mock

import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.remote.dto.MessageResult
import com.won983212.mongle.data.source.remote.dto.request.DiaryRequest
import com.won983212.mongle.data.source.remote.dto.response.CalendarDayDetailResponse
import com.won983212.mongle.data.source.remote.dto.response.CalendarDayPreviewResponse
import com.won983212.mongle.data.source.remote.dto.response.EmotionalSentenceResponse
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.util.generateCalendarDetailResponse1
import com.won983212.mongle.util.generateCalendarPreviewResponse1
import java.time.LocalDate
import java.time.YearMonth

class MockCalendarApi : CalendarApi {
    private var diary: String = ""
    private var emotion: Emotion? = null
    var calendarDayMetadataProducer: (YearMonth, YearMonth) -> List<CalendarDayPreviewResponse> =
        ::generateCalendarPreviewResponse1
    var calendarDayDetailProducer: (LocalDate, String, Emotion?) -> CalendarDayDetailResponse =
        ::generateCalendarDetailResponse1

    override suspend fun updateDiary(
        year: Int,
        month: String,
        day: String,
        text: DiaryRequest
    ): MessageResult {
        this.diary = text.text
        return MessageResult("ok")
    }

    override suspend fun updateEmotion(
        year: Int,
        month: String,
        day: String,
        emotion: String
    ): MessageResult {
        this.emotion = Emotion.of(emotion)
        return MessageResult("ok")
    }

    override suspend fun getCalendarDayMetadata(
        start: String,
        end: String
    ): List<CalendarDayPreviewResponse> {
        return calendarDayMetadataProducer(YearMonth.parse(start), YearMonth.parse(end))
    }

    override suspend fun getCalendarDayDetail(
        year: Int,
        month: String,
        day: String
    ): CalendarDayDetailResponse {
        val date = LocalDate.of(year, month.toInt(), day.toInt())
        return calendarDayDetailProducer(date, diary, emotion)
    }

    override suspend fun getDayEmotionalSentences(
        year: Int,
        month: String,
        day: String,
        emotion: String
    ): List<EmotionalSentenceResponse> {
        return listOf()
    }

}