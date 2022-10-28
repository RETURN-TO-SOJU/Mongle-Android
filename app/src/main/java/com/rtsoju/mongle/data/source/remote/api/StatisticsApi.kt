package com.rtsoju.mongle.data.source.remote.api

import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsApi {
    @GET("statistics/year")
    suspend fun getYearlyStatistics(
        @Query("year") year: Int
    ): StatisticsResponse<Float?>

    @GET("statistics/month")
    suspend fun getMonthlyStatistics(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): StatisticsResponse<Float?>

    @GET("statistics/week")
    suspend fun getWeeklyStatistics(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("week") week: Int
    ): StatisticsResponse<StatisticsResponse.DateScore>
}