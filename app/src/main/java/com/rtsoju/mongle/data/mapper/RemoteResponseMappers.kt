package com.rtsoju.mongle.data.mapper

import com.rtsoju.mongle.data.source.remote.dto.response.*
import com.rtsoju.mongle.domain.model.*
import java.time.LocalDate

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
    return EmotionalSentence(sentence, emotion)
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