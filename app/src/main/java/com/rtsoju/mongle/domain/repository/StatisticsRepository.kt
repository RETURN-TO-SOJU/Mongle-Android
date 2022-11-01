package com.rtsoju.mongle.domain.repository

import com.rtsoju.mongle.domain.model.StatisticsResult

interface StatisticsRepository {

    suspend fun getYearlyStatistics(
        year: Int
    ): Result<StatisticsResult>

    suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): Result<StatisticsResult>

    suspend fun getWeeklyStatistics(
        year: Int,
        month: Int,
        week: Int
    ): Result<StatisticsResult>
}