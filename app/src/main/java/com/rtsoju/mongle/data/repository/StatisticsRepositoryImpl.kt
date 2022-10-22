package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.source.remote.dto.RemoteStatisticsDataSource
import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import com.rtsoju.mongle.domain.repository.StatisticsRepository
import javax.inject.Inject

internal class StatisticsRepositoryImpl
@Inject constructor(
    private val remoteStatisticsDataSource: RemoteStatisticsDataSource
) : StatisticsRepository {
    override suspend fun getYearlyStatistics(
        year: Int
    ): Result<StatisticsResponse<Float?>> {
        return remoteStatisticsDataSource.getYearlyStatistics(year)
    }

    override suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): Result<StatisticsResponse<Float?>> {
        return remoteStatisticsDataSource.getMonthlyStatistics(year, month)
    }

    override suspend fun getWeeklyStatistics(
        year: Int,
        month: Int,
        week: Int
    ): Result<StatisticsResponse<StatisticsResponse.DateScore>> {
        return remoteStatisticsDataSource.getWeeklyStatistics(year, month, week)
    }

}