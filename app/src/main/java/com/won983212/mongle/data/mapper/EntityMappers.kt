package com.won983212.mongle.data.util

import com.won983212.mongle.data.source.local.entity.*
import com.won983212.mongle.data.source.remote.dto.response.*
import com.won983212.mongle.domain.model.*

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
    return EmotionalSentence(id, sentence, emotion)
}

fun Favorite.toEntity(): FavoriteEntity {
    return FavoriteEntity(id, emotion, date, title)
}