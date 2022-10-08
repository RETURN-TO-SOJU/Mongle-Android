package com.won983212.mongle.data.mapper

import com.won983212.mongle.data.source.local.entity.*
import com.won983212.mongle.domain.model.*
import java.time.LocalDate

fun FavoriteEntity.toDomainModel(): Favorite {
    return Favorite(id, emotion, date, title)
}

fun PhotoEntity.toDomainModel(): Photo {
    return Photo(url, time)
}

fun ScheduleEntity.toDomainModel(): Schedule {
    return Schedule(name, calendar, startTime, endTime)
}

fun EmotionProportionEntity.toDomainModel(): EmotionProportion {
    return EmotionProportion(emotion, percent)
}

fun EmotionalSentenceEntity.toDomainModel(): EmotionalSentence {
    return EmotionalSentence(sentence, emotion)
}

fun Favorite.toEntity(): FavoriteEntity {
    return FavoriteEntity(id, emotion, date, title)
}

fun Photo.toEntity(date: LocalDate): PhotoEntity {
    return PhotoEntity(0, date, url, time)
}

fun Schedule.toEntity(date: LocalDate): ScheduleEntity {
    return ScheduleEntity(0, date, name, calendar, startTime, endTime)
}

fun EmotionProportion.toEntity(date: LocalDate): EmotionProportionEntity {
    return EmotionProportionEntity(0, date, emotion, percent)
}

fun EmotionalSentence.toEntity(date: LocalDate): EmotionalSentenceEntity {
    return EmotionalSentenceEntity(0, date, sentence, emotion)
}

fun CalendarDayPreview.toCalendarDayEntity(): CalendarDayEntity {
    return CalendarDayEntity(
        date,
        emotion,
        keywords,
        "",
        ""
    )
}

fun CalendarDayDetail.toCalendarDayEntity(): CalendarDayEntity {
    return CalendarDayEntity(
        date,
        emotion,
        listOf(),
        diary,
        diaryFeedback
    )
}