package com.won983212.mongle.data.remote.api

import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.request.DiaryRequest
import com.won983212.mongle.data.remote.model.response.CalendarDay
import com.won983212.mongle.data.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.remote.model.response.EmotionalSentence
import retrofit2.http.*

interface CalendarApi {
    @POST("calender/{year}/{month}/{day}/diary")
    suspend fun updateDiary(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int,
        @Body text: DiaryRequest,
    ): MessageResult

    @GET("calender/{year}")
    suspend fun getCalendarDayMetadata(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Query("start") start: Int,
        @Query("end") end: Int
    ): List<CalendarDay>

    @GET("calender/{year}/{month}/{day}")
    suspend fun getCalendarDayDetail(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): CalendarDayDetail

    @GET("calender/{year}/{month}/{day}/sentences")
    suspend fun getDayEmotionalSentences(
        @Header(AUTH_TOKEN_HEADER) token: String,
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int,
        @Query("emotion") emotion: String
    ): List<EmotionalSentence>
}