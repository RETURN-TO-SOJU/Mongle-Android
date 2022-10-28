package com.rtsoju.mongle.domain.usecase.statistics

import com.rtsoju.mongle.domain.model.StatisticsResult
import com.rtsoju.mongle.domain.repository.StatisticsRepository
import javax.inject.Inject

/** 주간 감정 분석 통계를 가져옵니다.
 *  @param year 년. 2022와 같이 YYYY로 입력합니다.
 *  @param month 월 1~12사이의 값입니다.
 *  @param week 주 1~5사이의 값이고, 달마다 범위가 다릅니다. 목요일을 기준으로 한 주를 정합니다.
 *  */
class GetWeeklyStatisticsUseCase @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) {
    suspend operator fun invoke(
        year: Int,
        month: Int,
        week: Int
    ): Result<StatisticsResult> {
        return statisticsRepository.getWeeklyStatistics(year, month, week)
    }
}