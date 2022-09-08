package com.won983212.mongle.domain.repository

import com.won983212.mongle.data.model.Emotion
import com.won983212.mongle.data.source.remote.model.MessageResult
import com.won983212.mongle.data.source.remote.model.response.CalendarDay
import com.won983212.mongle.data.source.remote.model.response.CalendarDayDetail
import com.won983212.mongle.data.source.remote.model.response.EmotionalSentence
import java.time.LocalDate

interface CalendarRepository {

    /**
     * path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받은 후 body로 일기 내용을 입력받아 일기를 작성한다.
     * @param date 일기 작성일
     * @param text 일기 내용
     */
    suspend fun updateDiary(
        date: LocalDate,
        text: String
    ): Result<MessageResult>

    /**
     * query parameter로 필요한 년도 및 월을 받아 해당 월의 감정 및 주제들을 반환한다.
     * ex) /api/calender/{year}?start=3&end=5 : 3월부터 5월까지 데이터
     * @param startMonth 시작 월. 이 매개변수의 '일'은 무시됨
     * @param endMonth 끝 월. 이 달까지 포함해서 결과가 반환된다. 이 매개변수의 '일'은 무시됨
     */
    suspend fun getCalendarDayMetadata(
        startMonth: LocalDate,
        endMonth: LocalDate
    ): Result<List<CalendarDay>>

    /**
     * path variable로 필요한 년도, 월, 일(22/07/26)을 입력 받아 해당 일의 필요한 데이터를 조회한다.
     */
    suspend fun getCalendarDayDetail(
        date: LocalDate
    ): Result<CalendarDayDetail>

    /**
     * 특정 일의 분석화면에서 감정이를 눌렀을 때 해당하는 대화 문장들을 불러온다.
     */
    suspend fun getDayEmotionalSentences(
        date: LocalDate,
        emotion: Emotion
    ): Result<List<EmotionalSentence>>
}