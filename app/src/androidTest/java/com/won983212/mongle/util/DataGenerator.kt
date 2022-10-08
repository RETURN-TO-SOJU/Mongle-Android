package com.won983212.mongle.util

import com.won983212.mongle.data.source.local.entity.CalendarDayEntity
import com.won983212.mongle.domain.model.*
import java.time.LocalDate


fun generateCalendarDayEntity(date: LocalDate): CalendarDayEntity {
    return CalendarDayEntity(
        date,
        Emotion.ANGRY,
        listOf("Hello", "Nice", "안녕"),
        "하이",
        "좋은 하루였네요."
    )
}

fun generateDetailedCalendarDay1(date: LocalDate): CalendarDayDetail {
    return CalendarDayDetail(
        date,
        listOf(
            Photo("URL1", date.atTime(10, 10, 19)),
            Photo("URL2", date.atTime(15, 12, 21)),
            Photo("URL3", date.atTime(20, 15, 55)),
        ),
        "하이 일기장",
        "피드백",
        listOf(
            Schedule(
                "Schedule1",
                "Google",
                date.atTime(10, 0),
                date.atTime(11, 0)
            ),
            Schedule(
                "Schedule2",
                "Google",
                date.atTime(20, 30),
                date.atTime(21, 0)
            )
        ),
        listOf(
            EmotionProportion(Emotion.HAPPY, 30),
            EmotionProportion(Emotion.SAD, 20),
            EmotionProportion(Emotion.TIRED, 10),
            EmotionProportion(Emotion.NEUTRAL, 10),
            EmotionProportion(Emotion.ANXIOUS, 10),
            EmotionProportion(Emotion.ANGRY, 20),
        ),
        Emotion.HAPPY
    )
}

fun generateDetailedCalendarDay2(date: LocalDate): CalendarDayDetail {
    return CalendarDayDetail(
        date,
        listOf(
            Photo("OtherURL1", date.atTime(11, 10, 19)),
            Photo("OtherURL2", date.atTime(12, 12, 21)),
            Photo("OtherURL3", date.atTime(23, 15, 55)),
        ),
        "하이 일기장2",
        "피드백2",
        listOf(
            Schedule(
                "Schedule1111",
                "Google",
                date.atTime(10, 0),
                date.atTime(11, 0)
            ),
            Schedule(
                "Schedule2222",
                "Google",
                date.atTime(20, 30),
                date.atTime(21, 0)
            )
        ),
        listOf(
            EmotionProportion(Emotion.HAPPY, 30),
            EmotionProportion(Emotion.SAD, 15),
            EmotionProportion(Emotion.TIRED, 15),
            EmotionProportion(Emotion.NEUTRAL, 15),
            EmotionProportion(Emotion.ANXIOUS, 15),
            EmotionProportion(Emotion.ANGRY, 10),
        ),
        Emotion.HAPPY
    )
}

fun generateEmotionalSentences1(emotion: Emotion): List<EmotionalSentence> {
    return listOf(
        EmotionalSentence("Hello, Nice to meet you", emotion),
        EmotionalSentence("반갑습니다. 여러분들", emotion),
        EmotionalSentence("테스트 데이터", emotion),
        EmotionalSentence("두번째 데이터", emotion),
        EmotionalSentence("세번째 테스트 데이터", emotion),
    )
}

fun generateEmotionalSentences2(emotion: Emotion): List<EmotionalSentence> {
    return listOf(
        EmotionalSentence("zzzzzzzzzzzz", emotion),
        EmotionalSentence("hahahahahahahahahah", emotion),
        EmotionalSentence("한글 데이터", emotion),
    )
}