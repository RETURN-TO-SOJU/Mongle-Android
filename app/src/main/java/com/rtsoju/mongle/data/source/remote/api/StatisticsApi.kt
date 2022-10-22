package com.rtsoju.mongle.data.source.remote.api

import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface StatisticsApi {
    @POST("statistics/year")
    suspend fun getYearlyStatistics(
        @Query("year") year: Int
    ): StatisticsResponse<Float?>

    @POST("statistics/month")
    suspend fun getMonthlyStatistics(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): StatisticsResponse<Float?>

    @POST("statistics/week")
    suspend fun getWeeklyStatistics(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("week") week: Int
    ): StatisticsResponse<StatisticsResponse.DateScore>
}