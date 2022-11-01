package com.rtsoju.mongle.data.source.remote

import com.rtsoju.mongle.data.source.remote.api.StatisticsApi
import com.rtsoju.mongle.data.source.remote.api.safeApiCall
import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import javax.inject.Inject

internal class RemoteStatisticsDataSource @Inject constructor(
    private val api: StatisticsApi
) {
    suspend fun getYearlyStatistics(
        year: Int
    ): Result<StatisticsResponse<Float?>> {
        return safeApiCall {
            api.getYearlyStatistics(year)
        }
    }

    suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): Result<StatisticsResponse<Float?>> {
        return safeApiCall {
            api.getMonthlyStatistics(year, month)
        }
    }

    suspend fun getWeeklyStatistics(
        year: Int,
        month: Int,
        week: Int
    ): Result<StatisticsResponse<StatisticsResponse.DateScore>> {
        return safeApiCall {
            api.getWeeklyStatistics(year, month, week)
        }
    }
}