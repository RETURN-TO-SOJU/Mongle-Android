package com.won983212.mongle.data.di

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.model.OAuthLoginToken
import com.won983212.mongle.data.model.User
import com.won983212.mongle.data.remote.api.CalendarApi
import com.won983212.mongle.data.remote.api.KakaoSendApi
import com.won983212.mongle.data.remote.api.UserApi
import com.won983212.mongle.data.remote.model.MessageResult
import com.won983212.mongle.data.remote.model.request.DiaryRequest
import com.won983212.mongle.data.remote.model.request.FCMTokenRequest
import com.won983212.mongle.data.remote.model.response.CalendarDay
import com.won983212.mongle.data.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.remote.model.response.EmotionalSentence
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

class MockingHttpException(
    message: String
) : RuntimeException(message)

// TODO Mocking API
@Module
@InstallIn(SingletonComponent::class)
internal class MockingApiModule {

    private fun checkToken(token: String) {
        val tokenDate = LocalDateTime.parse(token, DateTimeFormatter.ISO_DATE_TIME)
        if (tokenDate.plusMinutes(10) < LocalDateTime.now()) {
            throw MockingHttpException("토큰이 만료되었습니다.")
        }
    }

    @Singleton
    @Provides
    fun provideLoginApi(): UserApi =
        object : UserApi {
            override suspend fun login(kakaoToken: OAuthLoginToken): OAuthLoginToken =
                withContext(Dispatchers.IO) {
                    delay(300)
                    val genToken = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    OAuthLoginToken(genToken, genToken)
                }

            override suspend fun getUserInfo(token: String): User =
                withContext(Dispatchers.IO) {
                    delay(300)
                    checkToken(token)
                    User("소마", "soma")
                }

            override suspend fun refreshToken(token: OAuthLoginToken): OAuthLoginToken =
                withContext(Dispatchers.IO) {
                    delay(500)
                    val genToken = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
                    OAuthLoginToken(genToken, genToken)
                }

            override suspend fun setFCMToken(
                token: String,
                fcmToken: FCMTokenRequest
            ): MessageResult =
                withContext(Dispatchers.IO) {
                    delay(500)
                    checkToken(token)
                    MessageResult("complete")
                }

            override suspend fun leaveAccount(token: String): MessageResult =
                withContext(Dispatchers.IO) {
                    delay(2000)
                    checkToken(token)
                    MessageResult("complete")
                }
        }

    @Singleton
    @Provides
    fun provideKakaoSendApi(): KakaoSendApi =
        object : KakaoSendApi {
            override suspend fun uploadKakaotalk(
                token: String,
                files: MultipartBody.Part
            ): MessageResult =
                withContext(Dispatchers.IO) {
                    checkToken(token)
                    delay(1000)
                    MessageResult("complete")
                }
        }

    @Singleton
    @Provides
    fun provideCalendarApi(): CalendarApi =
        object : CalendarApi {
            override suspend fun updateDiary(
                token: String,
                year: Int,
                month: Int,
                day: Int,
                text: DiaryRequest
            ): MessageResult =
                withContext(Dispatchers.IO) {
                    checkToken(token)
                    delay(500)
                    MessageResult("complete")
                }

            override suspend fun getCalendarDayMetadata(
                token: String,
                year: Int,
                start: Int,
                end: Int
            ): List<CalendarDay> =
                withContext(Dispatchers.IO) {
                    checkToken(token)
                    delay(1000)
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
                token: String,
                year: Int,
                month: Int,
                day: Int
            ): CalendarDayDetail =
                withContext(Dispatchers.IO) {
                    checkToken(token)
                    delay(1000)
                    CalendarDayDetail(
                        listOf("1.jpg", "2.jpg"), "오늘은 힘든날이 아니다.", listOf(
                            CalendarDayDetail.Schedule(
                                "기획발표",
                                LocalDateTime.of(2022, 8, 1, 10, 0, 0),
                                LocalDateTime.of(2022, 8, 1, 12, 0, 0)
                            )
                        ), listOf(
                            CalendarDayDetail.EmotionProportion(Emotion.HAPPY, 15),
                            CalendarDayDetail.EmotionProportion(Emotion.SAD, 20),
                            CalendarDayDetail.EmotionProportion(Emotion.ANXIOUS, 25),
                            CalendarDayDetail.EmotionProportion(Emotion.TIRED, 30),
                            CalendarDayDetail.EmotionProportion(Emotion.ANGRY, 35),
                            CalendarDayDetail.EmotionProportion(Emotion.NEUTRAL, 40)
                        )
                    )
                }

            override suspend fun getDayEmotionalSentences(
                token: String,
                year: Int,
                month: Int,
                day: Int,
                emotion: String
            ): List<EmotionalSentence> =
                withContext(Dispatchers.IO) {
                    checkToken(token)
                    delay(1000)
                    listOf(
                        EmotionalSentence(0, "아 슬퍼", Emotion.SAD),
                        EmotionalSentence(1, "아 너무 슬퍼", Emotion.SAD),
                        EmotionalSentence(2, "아 정말 슬퍼", Emotion.SAD),
                        EmotionalSentence(3, "기뻐", Emotion.HAPPY),
                        EmotionalSentence(4, "아 정말 기뻐", Emotion.HAPPY)
                    )
                }
        }
}