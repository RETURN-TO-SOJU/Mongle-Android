package com.won983212.mongle.util

import com.won983212.mongle.data.source.local.entity.CalendarDayEntity
import com.won983212.mongle.data.source.remote.dto.response.CalendarDayDetailResponse
import com.won983212.mongle.data.source.remote.dto.response.CalendarDayPreviewResponse
import com.won983212.mongle.domain.model.*
import java.time.LocalDate
import java.time.YearMonth


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

fun generateCalendarPreviewResponse1(
    from: YearMonth,
    to: YearMonth
): List<CalendarDayPreviewResponse> {
    return listOf(
        CalendarDayPreviewResponse(
            from.atDay(1), Emotion.SAD, listOf("hi")
        ),
        CalendarDayPreviewResponse(
            from.atDay(10), Emotion.HAPPY, listOf("hello")
        ),
        CalendarDayPreviewResponse(
            from.atDay(15), Emotion.TIRED, listOf("")
        ),
        CalendarDayPreviewResponse(
            from.atDay(20), Emotion.NEUTRAL, listOf("hello")
        ),
        CalendarDayPreviewResponse(
            to.atDay(5), Emotion.NEUTRAL, listOf("good gggg")
        ),
        CalendarDayPreviewResponse(
            to.atDay(8), Emotion.TIRED, listOf("")
        ),
        CalendarDayPreviewResponse(
            to.atDay(17), Emotion.HAPPY, listOf("")
        ),
        CalendarDayPreviewResponse(
            to.atDay(26), Emotion.SAD, listOf("good")
        )
    )
}

fun generateCalendarPreviewResponse2(
    from: YearMonth,
    to: YearMonth
): List<CalendarDayPreviewResponse> {
    return listOf(
        CalendarDayPreviewResponse(
            from.atDay(1), Emotion.HAPPY, listOf("ddddddddddddd")
        ),
        CalendarDayPreviewResponse(
            from.atDay(10), Emotion.SAD, listOf("ccccccccccc")
        ),
        CalendarDayPreviewResponse(
            from.atDay(15), Emotion.HAPPY, listOf("")
        ),
        CalendarDayPreviewResponse(
            from.atDay(20), Emotion.ANXIOUS, listOf("aaaaaaaaaaaa")
        ),
        CalendarDayPreviewResponse(
            to.atDay(5), Emotion.SAD, listOf("hhhhhhhhhhhh")
        ),
        CalendarDayPreviewResponse(
            to.atDay(8), Emotion.ANXIOUS, listOf("awaw")
        ),
        CalendarDayPreviewResponse(
            to.atDay(17), Emotion.SAD, listOf("esgseg")
        ),
        CalendarDayPreviewResponse(
            to.atDay(26), Emotion.HAPPY, listOf("jk")
        )
    )
}

fun generateCalendarDetailResponse1(
    date: LocalDate,
    diary: String,
    emotion: Emotion?
): CalendarDayDetailResponse {
    return CalendarDayDetailResponse(
        listOf(
            CalendarDayDetailResponse.PhotoResponse(
                "1.jpg",
                date.atTime(10, 0, 0)
            ),
            CalendarDayDetailResponse.PhotoResponse(
                "2.jpg",
                date.atTime(11, 0, 0)
            ),
            CalendarDayDetailResponse.PhotoResponse(
                "3.jpg",
                date.atTime(12, 0, 0)
            ),
        ),
        diary,
        "좋은 하루였다고 생각합니다.",
        listOf(
            CalendarDayDetailResponse.ScheduleResponse(
                "기획발표",
                "네이버캘린더",
                date.atTime(10, 0, 0),
                date.atTime(12, 0, 0)
            ),
            CalendarDayDetailResponse.ScheduleResponse(
                "중간발표",
                "네이버캘린더",
                date.atTime(13, 0, 0),
                date.atTime(15, 0, 0)
            ),
        ),
        listOf(
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.HAPPY, 15),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.SAD, 20),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.ANXIOUS, 25),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.TIRED, 30),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.ANGRY, 35),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.NEUTRAL, 40)
        ),
        emotion
    )
}

fun generateCalendarDetailResponse2(
    date: LocalDate,
    diary: String,
    emotion: Emotion?
): CalendarDayDetailResponse {
    return CalendarDayDetailResponse(
        listOf(
            CalendarDayDetailResponse.PhotoResponse(
                "abc2.jpg",
                date.atTime(10, 50, 0)
            ),
            CalendarDayDetailResponse.PhotoResponse(
                "kdm2.jpg",
                date.atTime(11, 40, 0)
            ),
            CalendarDayDetailResponse.PhotoResponse(
                "kotlin2.jpg",
                date.atTime(16, 30, 0)
            ),
        ),
        diary,
        "기분좋은 하루였다고 생각합니다 아마도",
        listOf(
            CalendarDayDetailResponse.ScheduleResponse(
                "기획발표2",
                "네이버캘린더",
                date.atTime(20, 10, 0),
                date.atTime(21, 10, 0)
            ),
            CalendarDayDetailResponse.ScheduleResponse(
                "중간발표2",
                "네이버캘린더",
                date.atTime(16, 50, 0),
                date.atTime(18, 0, 0)
            ),
        ),
        listOf(
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.HAPPY, 20),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.SAD, 10),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.ANXIOUS, 20),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.TIRED, 10),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.ANGRY, 10),
            CalendarDayDetailResponse.EmotionProportionResponse(Emotion.NEUTRAL, 30)
        ),
        emotion
    )
}

fun generateUser1(): User {
    return User("Soma", "soma_123")
}

fun generateUser2(): User {
    return User("가나 초콜릿", "gana_초콜릿")
}