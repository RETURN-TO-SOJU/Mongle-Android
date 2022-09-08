package com.won983212.mongle.debug.mock

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.api.CalendarApi
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.request.DiaryRequest
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import com.won983212.mongle.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class MockCalendarApi(
    private val authRepository: AuthRepository
) : CalendarApi {

    override suspend fun updateDiary(
        year: Int,
        month: String,
        day: String,
        text: DiaryRequest
    ): MessageResult =
        withContext(Dispatchers.IO) {
            delay(500)
            MockAuthApi.checkToken(authRepository)
            MessageResult("complete")
        }

    override suspend fun getCalendarDayMetadata(
        start: String,
        end: String
    ): List<CalendarDay> =
        withContext(Dispatchers.IO) {
            delay(1000)
            MockAuthApi.checkToken(authRepository)
            listOf(
                CalendarDay(LocalDate.of(2022, 8, 1), Emotion.HAPPY, listOf("학교", "소마")),
                CalendarDay(LocalDate.of(2022, 8, 2), Emotion.SAD, listOf("학교2", "소마1")),
                CalendarDay(
                    LocalDate.of(2022, 8, 4),
                    Emotion.ANXIOUS,
                    listOf("학교3", "소마2", "노래방1")
                ),
                CalendarDay(LocalDate.of(2022, 8, 7), Emotion.SAD, listOf("학교4", "소마3")),
                CalendarDay(
                    LocalDate.of(2022, 8, 11),
                    Emotion.HAPPY,
                    listOf("학교5", "소마4", "노래방2")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 12),
                    Emotion.NEUTRAL,
                    listOf("학교6", "소마5")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 13),
                    Emotion.HAPPY,
                    listOf("학교7a", "소마6", "노래방3")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 16),
                    Emotion.NEUTRAL,
                    listOf("학교7b", "소마6", "노래방3")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 19),
                    Emotion.HAPPY,
                    listOf("학교7c", "소마6", "노래방3")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 20),
                    Emotion.NEUTRAL,
                    listOf("학교7d", "소마6", "노래방3")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 22),
                    Emotion.SAD,
                    listOf("학교7e", "소마6", "노래방3")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 24),
                    Emotion.HAPPY,
                    listOf("학교7f", "소마6", "노래방3")
                ),
                CalendarDay(
                    LocalDate.of(2022, 8, 25),
                    Emotion.HAPPY,
                    listOf("학교7g", "소마6", "노래방3")
                ),
                CalendarDay(LocalDate.of(2022, 8, 28), Emotion.TIRED, listOf("학교8", "소마7")),
            )
        }

    override suspend fun getCalendarDayDetail(
        year: Int,
        month: String,
        day: String
    ): CalendarDayDetail =
        withContext(Dispatchers.IO) {
            delay(1000)
            MockAuthApi.checkToken(authRepository)
            CalendarDayDetail(
                listOf(
                    CalendarDayDetail.Photo(
                        "1.jpg",
                        LocalDateTime.of(2022, 8, 1, 10, 0, 0)
                    ),
                    CalendarDayDetail.Photo(
                        "2.jpg",
                        LocalDateTime.of(2022, 8, 1, 11, 0, 0)
                    ),
                    CalendarDayDetail.Photo(
                        "3.jpg",
                        LocalDateTime.of(2022, 8, 1, 17, 0, 0)
                    ),
                ),
                "오늘은 힘든날이 아니다.",
                "좋은 하루였다고 생각합니다.",
                listOf(
                    CalendarDayDetail.Schedule(
                        "기획발표",
                        "네이버캘린더",
                        LocalDateTime.of(2022, 8, 1, 10, 0, 0),
                        LocalDateTime.of(2022, 8, 1, 12, 0, 0)
                    ),
                    CalendarDayDetail.Schedule(
                        "중간발표",
                        "네이버캘린더",
                        LocalDateTime.of(2022, 8, 5, 12, 0, 0),
                        LocalDateTime.of(2022, 8, 5, 21, 0, 0)
                    ),
                ),
                listOf(
                    CalendarDayDetail.EmotionProportion(Emotion.HAPPY, 15),
                    CalendarDayDetail.EmotionProportion(Emotion.SAD, 20),
                    CalendarDayDetail.EmotionProportion(Emotion.ANXIOUS, 25),
                    CalendarDayDetail.EmotionProportion(Emotion.TIRED, 30),
                    CalendarDayDetail.EmotionProportion(Emotion.ANGRY, 35),
                    CalendarDayDetail.EmotionProportion(Emotion.NEUTRAL, 40)
                ),
                Emotion.ANGRY
            )
        }

    override suspend fun getDayEmotionalSentences(
        year: Int,
        month: String,
        day: String,
        emotion: String
    ): List<EmotionalSentence> =
        withContext(Dispatchers.IO) {
            delay(1000)
            MockAuthApi.checkToken(authRepository)
            when (emotion) {
                Emotion.HAPPY.name -> (0..4).map {
                    EmotionalSentence(
                        it.toLong(),
                        "행복${it}",
                        Emotion.HAPPY
                    )
                }
                Emotion.ANXIOUS.name -> (0..4).map {
                    EmotionalSentence(
                        it.toLong(),
                        "불안${it}",
                        Emotion.ANXIOUS
                    )
                }
                Emotion.ANGRY.name -> (0..4).map {
                    EmotionalSentence(
                        it.toLong(),
                        "화난다${it}",
                        Emotion.ANGRY
                    )
                }
                Emotion.SAD.name -> (0..4).map {
                    EmotionalSentence(
                        it.toLong(),
                        "슬프다${it}",
                        Emotion.SAD
                    )
                }
                Emotion.NEUTRAL.name -> (0..4).map {
                    EmotionalSentence(
                        it.toLong(),
                        "평범하다${it}",
                        Emotion.NEUTRAL
                    )
                }
                Emotion.TIRED.name -> (0..4).map {
                    EmotionalSentence(
                        it.toLong(),
                        "힘들다${it}",
                        Emotion.TIRED
                    )
                }
                else -> listOf()
            }
        }
}