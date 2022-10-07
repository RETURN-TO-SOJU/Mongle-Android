package com.won983212.mongle.data.util

import com.won983212.mongle.data.source.remote.dto.response.*
import com.won983212.mongle.domain.model.*

fun CalendarDayDetailResponse.toDomainModel(): CalendarDayDetail {
    return CalendarDayDetail(
        imageList.map { it.toDomainModel() },
        diary,
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
    return EmotionalSentence(id, sentence, emotion)
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