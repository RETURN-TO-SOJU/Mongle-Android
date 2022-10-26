package com.rtsoju.mongle.data.repository

import com.rtsoju.mongle.data.mapper.toMonthlyDomainModel
import com.rtsoju.mongle.data.mapper.toWeeklyDomainModel
import com.rtsoju.mongle.data.mapper.toYearlyDomainModel
import com.rtsoju.mongle.data.source.remote.RemoteStatisticsDataSource
import com.rtsoju.mongle.domain.model.StatisticsResult
import com.rtsoju.mongle.domain.repository.StatisticsRepository
import javax.inject.Inject

internal class StatisticsRepositoryImpl
@Inject constructor(
    private val remoteStatisticsDataSource: RemoteStatisticsDataSource
) : StatisticsRepository {
    override suspend fun getYearlyStatistics(
        year: Int
    ): Result<StatisticsResult> {
        return remoteStatisticsDataSource.getYearlyStatistics(year)
            .map { it.toYearlyDomainModel() }
    }

    override suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): Result<StatisticsResult> {
        return remoteStatisticsDataSource.getMonthlyStatistics(year, month)
            .map { it.toMonthlyDomainModel() }
    }

    override suspend fun getWeeklyStatistics(
        year: Int,
        month: Int,
        week: Int
    ): Result<StatisticsResult> {
        return remoteStatisticsDataSource.getWeeklyStatistics(year, month, week)
            .map { it.toWeeklyDomainModel() }
    }

}