package com.rtsoju.mongle.domain.usecase.statistics

import com.rtsoju.mongle.domain.model.StatisticsResult
import com.rtsoju.mongle.domain.repository.StatisticsRepository
import javax.inject.Inject

/** 월간 감정 분석 통계를 가져옵니다.
 *  @param year 년. 2022와 같이 YYYY로 입력합니다.
 *  @param month 월 1~12사이의 값입니다. */
class GetMonthlyStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int
    ): Result<StatisticsResult> {
        return statisticsRepository.getMonthlyStatistics(year, month)
    }
}