package com.rtsoju.mongle.data.mapper

import com.google.common.truth.Truth
import com.rtsoju.mongle.data.source.remote.dto.response.StatisticsResponse
import com.rtsoju.mongle.domain.model.Emotion
import org.junit.Test
import java.time.LocalDate


internal class StatisticsMapperTest {

    private fun <T> packStatisticsEmotions(statisticsData: StatisticsResponse.Data<T>): List<Int> {
        val result = IntArray(Emotion.values().size)
        result[Emotion.HAPPY.ordinal] = statisticsData.happy
        result[Emotion.ANGRY.ordinal] = statisticsData.angry
        result[Emotion.ANXIOUS.ordinal] = statisticsData.anxious
        result[Emotion.SAD.ordinal] = statisticsData.sad
        result[Emotion.NEUTRAL.ordinal] = statisticsData.neutral
        result[Emotion.TIRED.ordinal] = statisticsData.tired
        return result.asList()
    }

    @Test
    fun toYearlyDomainModel() {
        val date = LocalDate.now()
        val scores = listOf(
            20.0f,
            10.0f,
            null,
            null,
            null,
            null,
            20.12f,
            23.54f,
            1.24f,
            24.6f,
            12.41f,
            23.5f
        )
        val data = StatisticsResponse.Data(
            date,
            scores,
            10, 20, 30, 40, 50, 60
        )

        val model = StatisticsResponse(data).toYearlyDomainModel()
        Truth.assertThat(model.scores.map { it.score }).containsExactlyElementsIn(scores)
        Truth.assertThat(model.scores.map { it.label })
            .containsExactlyElementsIn((1..12).map { "${it}월" })
        Truth.assertThat(model.emotionCount).containsExactlyElementsIn(packStatisticsEmotions(data))
    }

    @Test
    fun toMonthlyDomainModel() {
        val date = LocalDate.now()
        val scores = listOf(
            20.0f,
            10.0f,
            null,
            null,
            null
        )
        val data = StatisticsResponse.Data(
            date,
            scores,
            10, 20, 30, 40, 50, 60
        )

        val model = StatisticsResponse(data).toMonthlyDomainModel()
        Truth.assertThat(model.scores.map { it.score }).containsExactlyElementsIn(scores)
        Truth.assertThat(model.scores.map { it.label })
            .containsExactlyElementsIn((1..scores.size).map { "${it}주" })
        Truth.assertThat(model.emotionCount).containsExactlyElementsIn(packStatisticsEmotions(data))
    }

    @Test
    fun toWeeklyDomainModel() {
        val date = LocalDate.of(2022, 9, 16)
        val scores = listOf(
            StatisticsResponse.DateScore(20.0f, LocalDate.of(2022, 9, 16)),
            StatisticsResponse.DateScore(21.0f, LocalDate.of(2022, 9, 18)),
            StatisticsResponse.DateScore(22.0f, LocalDate.of(2022, 9, 19)),
            StatisticsResponse.DateScore(24.0f, LocalDate.of(2022, 9, 20)),
        )
        val data = StatisticsResponse.Data(
            date,
            scores,
            10, 20, 30, 40, 50, 60
        )

        val expectedResult = listOf(20.0f, null, 21.0f, 22.0f, 24.0f, null, null)
        val model = StatisticsResponse(data).toWeeklyDomainModel()
        Truth.assertThat(model.scores.map { it.score }).containsExactlyElementsIn(expectedResult)
        Truth.assertThat(model.scores.map { it.label })
            .containsExactlyElementsIn((0 until 7).map {
                val idxDate = date.plusDays(it.toLong())
                "${idxDate.dayOfMonth}일"
            })
        Truth.assertThat(model.emotionCount).containsExactlyElementsIn(packStatisticsEmotions(data))
    }
}