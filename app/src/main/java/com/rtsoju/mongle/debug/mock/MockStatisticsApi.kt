package com.rtsoju.mongle.debug.mock

import com.rtsoju.mongle.data.source.remote.api.StatisticsApi
import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import java.time.LocalDate

class MockStatisticsApi : StatisticsApi {
    override suspend fun getYearlyStatistics(
        year: Int
    ): StatisticsResponse<Float?> {
        return StatisticsResponse(
            StatisticsResponse.Data(
                LocalDate.now(),
                listOf(
                    null,
                    50.4f,
                    40.4f,
                    12.5f,
                    40.4f,
                    32.5f,
                    null,
                    null,
                    62.4f,
                    52.5f,
                    12.4f,
                    51.5f
                ),
                517,
                315,
                123,
                112,
                112,
                322
            )
        )
    }

    override suspend fun getMonthlyStatistics(
        year: Int,
        month: Int
    ): StatisticsResponse<Float?> {
        return StatisticsResponse(
            StatisticsResponse.Data(
                LocalDate.now(),
                listOf(
                    null,
                    50.4f,
                    40.4f,
                    12.5f,
                    40.4f
                ),
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
        return StatisticsResponse(
            StatisticsResponse.Data(
                LocalDate.now(),
                listOf(
                    StatisticsResponse.DateScore(20.3f, LocalDate.of(year, month, 2)),
                    StatisticsResponse.DateScore(25.3f, LocalDate.of(year, month, 3)),
                    StatisticsResponse.DateScore(31.3f, LocalDate.of(year, month, 4)),
                    StatisticsResponse.DateScore(21.3f, LocalDate.of(year, month, 6)),
                    StatisticsResponse.DateScore(10.3f, LocalDate.of(year, month, 7)),
                ),
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