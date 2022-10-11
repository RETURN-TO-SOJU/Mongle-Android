package com.rtsoju.mongle.data.source.api

import com.rtsoju.mongle.data.source.remote.dto.MessageResult
import com.rtsoju.mongle.data.source.remote.dto.request.DiaryRequest
import com.rtsoju.mongle.data.source.remote.dto.response.CalendarDayDetailResponse
import com.rtsoju.mongle.data.source.remote.dto.response.CalendarDayPreviewResponse
import com.rtsoju.mongle.data.source.remote.dto.response.EmotionalSentenceResponse
import retrofit2.http.*

interface CalendarApi {
    @POST("calender/{year}/{month}/{day}/diary")
    suspend fun updateDiary(
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String,
        @Body text: DiaryRequest
    ): MessageResult

    @PATCH("calender/{year}/{month}/{day}/emotion")
    suspend fun updateEmotion(
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String,
        @Query("value") emotion: String
    ): MessageResult

    @GET("calender")
    suspend fun getCalendarDayMetadata(
        @Query("start") start: String,
        @Query("end") end: String
    ): List<CalendarDayPreviewResponse>

    @GET("calender/{year}/{month}/{day}")
    suspend fun getCalendarDayDetail(
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String
    ): CalendarDayDetailResponse

    @GET("calender/{year}/{month}/{day}/sentences")
    suspend fun getDayEmotionalSentences(
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String,
        @Query("emotion") emotion: String
    ): List<EmotionalSentenceResponse>
}