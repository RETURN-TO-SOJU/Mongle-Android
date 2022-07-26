package com.rtsoju.mongle.data.mapper

import com.rtsoju.mongle.data.source.remote.dto.response.*
import com.rtsoju.mongle.domain.model.*
import com.rtsoju.mongle.exception.InvalidResponseException
import java.time.LocalDate
import java.time.Period

fun CalendarDayDetailResponse.toDomainModel(date: LocalDate): CalendarDayDetail {
    return CalendarDayDetail(
        date,
        imageList.map { it.toDomainModel() },
        diary ?: "",
        diaryFeedback,
        scheduleList.map { it.toDomainModel() },
        emotionList.map { it.toDomainModel() },
        emotion
    )
}

fun CalendarDayDetailResponse.ScheduleResponse.toDomainModel(): Schedule {
    return Schedule(name, calendar, startTime, endTime)
}

fun CalendarDayDetailResponse.EmotionProportionResponse.toDomainModel(): EmotionProportion {
    return EmotionProportion(emotion, percent)
}

fun CalendarDayDetailResponse.PhotoResponse.toDomainModel(): Photo {
    return Photo(url, time)
}

fun CalendarDayPreviewResponse.toDomainModel(): CalendarDayPreview {
    return CalendarDayPreview(date, emotion, subjectList)
}

fun EmotionalSentenceResponse.toDomainModel(): EmotionalSentence {
    return EmotionalSentence(sentence, emotion, roomName)
}

fun LoginResponse.toDomainModel(): LoginResult {
    return LoginResult(
        name,
        accessToken,
        accessTokenExpiresAt,
        refreshToken,
        refreshTokenExpiresAt,
        isNew
    )

}

fun UserResponse.toDomainModel(): User {
    return User(username, kakaoName)
}


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

fun StatisticsResponse<Float?>.toYearlyDomainModel(): StatisticsResult {
    return StatisticsResult(
        data.scoreList.mapIndexed { idx, score -> StatisticsResult.Score("${idx + 1}월", score) },
        packStatisticsEmotions(data)
    )
}

fun StatisticsResponse<Float?>.toMonthlyDomainModel(): StatisticsResult {
    return StatisticsResult(
        data.scoreList.mapIndexed { idx, score -> StatisticsResult.Score("${idx + 1}주", score) },
        packStatisticsEmotions(data)
    )
}

fun StatisticsResponse<StatisticsResponse.DateScore>.toWeeklyDomainModel(): StatisticsResult {
    val startDate = data.startDate
    val scores = Array<Float?>(7) { null }

    data.scoreList.map {
        val idx = Period.between(startDate, it.date).days
        if (idx !in scores.indices) {
            throw InvalidResponseException()
        }
        scores[idx] = it.score
    }

    return StatisticsResult(
        scores.mapIndexed { idx, score ->
            val day = startDate.plusDays(idx.toLong())
            StatisticsResult.Score("${day.dayOfMonth}일", score)
        },
        packStatisticsEmotions(data)
    )
}