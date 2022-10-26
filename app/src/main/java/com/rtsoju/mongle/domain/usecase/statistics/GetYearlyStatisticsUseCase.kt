package com.rtsoju.mongle.domain.usecase.statistics

import com.rtsoju.mongle.domain.model.StatisticsResult
import com.rtsoju.mongle.domain.repository.StatisticsRepository
import javax.inject.Inject

/** 연간 감정 분석 통계를 가져옵니다.
 *  @param year 년. 2022와 같이 YYYY로 입력합니다. */
class GetYearlyStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(year: Int): Result<StatisticsResult> {
        return statisticsRepository.getYearlyStatistics(year)
    }
}