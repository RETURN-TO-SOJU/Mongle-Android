package com.rtsoju.mongle.data.mapper

import com.rtsoju.mongle.data.db.entity.*
import com.rtsoju.mongle.domain.model.*
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

fun Map<String, Int>.toDomainModel(): List<EmotionProportion> {
    return entries.map {
        EmotionProportion(Emotion.of(it.key), it.value)
    }
}

fun EmotionalSentenceEntity.toDomainModel(): EmotionalSentence {
    return EmotionalSentence(sentence, emotion, roomName)
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

fun EmotionalSentence.toEntity(date: LocalDate): EmotionalSentenceEntity {
    return EmotionalSentenceEntity(0, date, sentence, emotion, roomName)
}

fun List<EmotionProportion>.toEntity(): Map<String, Int> {
    return associate {
        it.emotion.name to it.percent
    }
}

fun CalendarDayPreview.toCalendarDayEntity(): CalendarDayEntity {
    return CalendarDayEntity(
        date,
        emotion,
        keywords,
        "",
        "",
        EmotionProportion.defaultProportionMap()
    )
}

fun CalendarDayDetail.toCalendarDayEntity(): CalendarDayEntity {
    return CalendarDayEntity(
        date,
        emotion,
        listOf(),
        diary,
        diaryFeedback,
        emotionList.toEntity()
    )
}