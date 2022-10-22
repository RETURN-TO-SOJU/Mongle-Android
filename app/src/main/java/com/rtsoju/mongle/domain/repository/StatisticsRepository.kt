package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse

interface StatisticsRepository {

    suspend fun getYearlyStatistics(
        year: Int
    ): Result<StatisticsResponse<Float?>>

    suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): Result<StatisticsResponse<Float?>>

    suspend fun getWeeklyStatistics(
        year: Int,
        month: Int,
        week: Int
    ): Result<StatisticsResponse<StatisticsResponse.DateScore>>
}