package com.won983212.mongle.data.source.api

import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.DiaryRequest
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import retrofit2.http.*

interface CalendarApi {
    @POST("calender/{year}/{month}/{day}/diary")
    suspend fun updateDiary(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String,
        @Body text: DiaryRequest,
    ): MessageResult

    @GET("calender")
    suspend fun getCalendarDayMetadata(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Query("start") start: String,
        @Query("end") end: String
    ): List<CalendarDay>

    @GET("calender/{year}/{month}/{day}")
    suspend fun getCalendarDayDetail(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String
    ): CalendarDayDetail

    @GET("calender/{year}/{month}/{day}/sentences")
    suspend fun getDayEmotionalSentences(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Path("month") month: String,
        @Path("day") day: String,
        @Query("emotion") emotion: String
    ): List<EmotionalSentence>
}