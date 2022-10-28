package com.rtsoju.mongle.debug.mock

import com.rtsoju.mongle.data.source.remote.api.StatisticsApi
import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import com.rtsoju.mongle.presentation.view.statistics.range.WeekRange
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

class MockStatisticsApi : StatisticsApi {
    private val rand = Random()

    private fun generateScores(size: Int): List<Float?> {
        val result = mutableListOf<Float?>()
        for (i in 1..size) {
            if (rand.nextInt(3) == 0) {
                result.add(null)
            } else {
                result.add(rand.nextFloat() * 100)
            }
        }
        return result
    }

    override suspend fun getYearlyStatistics(
        year: Int
    ): StatisticsResponse<Float?> {
        return StatisticsResponse(
            StatisticsResponse.Data(
                LocalDate.of(year, 1, 1),
                generateScores(12),
                5170,
                3150,
                1230,
                1120,
                1120,
                3220
            )
        )
    }

    override suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): StatisticsResponse<Float?> {
        val weeks = WeekRange.getCountOfWeeks(YearMonth.of(year, month))
        return StatisticsResponse(
            StatisticsResponse.Data(
                LocalDate.of(year, month, 1),
                generateScores(weeks),
                57,
                35,
                23,
                12,
                12,
                22
            )
        )
    }

    override suspend fun getWeeklyStatistics(
        year: Int,
        month: Int,
        week: Int
    ): StatisticsResponse<StatisticsResponse.DateScore> {
        val weekRange = WeekRange(year, month, week)
        val weekStart = weekRange.getRangeStart()
        val scores = generateScores(7)

        return StatisticsResponse(
            StatisticsResponse.Data(
                weekStart,
                scores.mapIndexed { idx, score ->
                    if (score != null) {
                        StatisticsResponse.DateScore(score, weekStart.plusDays(idx.toLong()))
                    } else {
                        null
                    }
                }.filterNotNull(),
                18,
                6,
                3,
                2,
                5,
                0
            )
        )
    }
}