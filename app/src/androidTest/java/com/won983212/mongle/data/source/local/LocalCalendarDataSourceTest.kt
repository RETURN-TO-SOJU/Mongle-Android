package com.won983212.mongle.data.source.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.won983212.mongle.data.db.AppDatabase
import com.won983212.mongle.data.db.CalendarDao
import com.won983212.mongle.domain.model.CalendarDayPreview
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.exception.NoResultException
import com.won983212.mongle.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.YearMonth

// TODO 일반 Unit테스트로 변경
@RunWith(AndroidJUnit4::class)
internal class LocalCalendarDataSourceTest {
    private lateinit var database: AppDatabase
    private lateinit var calendarDao: CalendarDao
    private lateinit var dataSource: LocalCalendarDataSource

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        calendarDao = database.calenderDao()
        dataSource = LocalCalendarDataSource(database)
    }

    @After
    fun after() {
        database.close()
    }

    private suspend fun insertWeeklyTestEntity(date: LocalDate, len: Int = 20, space: Int = 7) {
        for (i in 0 until len) {
            val data = generateCalendarDayEntity(date.plusDays((i * space).toLong()))
            calendarDao.insertCalendarDay(data)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_empty(): Unit = runTest {
        val from = YearMonth.of(2022, 8)
        val to = YearMonth.of(2022, 12)

        testFailedResult(dataSource.getCalendarDayPreview(from, to)) {
            assertThat(it).isInstanceOf(NoResultException::class.java)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day(): Unit = runTest {
        val from = YearMonth.of(2022, 8)
        val to = YearMonth.of(2022, 9)
        insertWeeklyTestEntity(from.atDay(1))

        testSuccessResult(dataSource.getCalendarDayPreview(from, to)) {
            assertThat(it.size).isEqualTo(9)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_calendar_day_preview_insert(): Unit = runTest {
        val date = LocalDate.of(2022, 8, 1)
        val testDatas = mutableListOf<CalendarDayPreview>()

        // precached:          3     5     7     9
        //   updates:  8.1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
        insertWeeklyTestEntity(date.plusDays(2), 4, 2)
        for (i in 0..10) {
            val testData = CalendarDayPreview(
                date.plusDays(i.toLong()),
                Emotion.HAPPY,
                listOf("HI")
            )
            testDatas.add(testData)
        }

        val fromAndTo = YearMonth.of(2022, 8)
        testSuccessResult(dataSource.getCalendarDayPreview(fromAndTo, fromAndTo)) {
            assertThat(it.size).isEqualTo(4)
        }

        dataSource.updateCalendarDayPreview(testDatas)

        testSuccessResult(dataSource.getCalendarDayPreview(fromAndTo, fromAndTo)) {
            assertThat(it.size).isEqualTo(11)
            assertThat(it).containsExactlyElementsIn(testDatas)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_calendar_day_detail() = runTest {
        val date = LocalDate.of(2022, 8, 20)
        val preCachedData = generateDetailedCalendarDay1(date)
        val updatedData = generateDetailedCalendarDay2(date)

        testFailedResult(dataSource.getCalendarDayDetail(date)) {
            assertThat(it).isInstanceOf(NoResultException::class.java)
        }

        dataSource.updateCalendarDayDetail(preCachedData)
        testSuccessResult(dataSource.getCalendarDayDetail(date)) {
            assertThat(it).isEqualTo(preCachedData)
        }

        dataSource.updateCalendarDayDetail(updatedData)
        testSuccessResult(dataSource.getCalendarDayDetail(date)) {
            assertThat(it).isEqualTo(updatedData)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_day_emotional_sentences() = runTest {
        val date = LocalDate.of(2022, 8, 20)
        val emotion = Emotion.SAD
        val emotion2 = Emotion.HAPPY
        val firstSentences = generateEmotionalSentences1(emotion)
        val firstSentences2 = generateEmotionalSentences1(emotion2)
        val secondSentences = generateEmotionalSentences2(emotion)

        testFailedResult(dataSource.getDayEmotionalSentences(date, emotion)) {
            assertThat(it).isInstanceOf(NoResultException::class.java)
        }

        dataSource.updateDayEmotionalSentences(date, emotion, firstSentences)
        testFailedResult(dataSource.getDayEmotionalSentences(date, emotion2)) {
            assertThat(it).isInstanceOf(NoResultException::class.java)
        }
        testSuccessResult(dataSource.getDayEmotionalSentences(date, emotion)) {
            assertThat(it).containsExactlyElementsIn(firstSentences)
        }

        dataSource.updateDayEmotionalSentences(date, emotion, secondSentences)
        dataSource.updateDayEmotionalSentences(date, emotion2, firstSentences2)
        testSuccessResult(dataSource.getDayEmotionalSentences(date, emotion)) {
            assertThat(it).containsExactlyElementsIn(secondSentences)
        }
        testSuccessResult(dataSource.getDayEmotionalSentences(date, emotion2)) {
            assertThat(it).containsExactlyElementsIn(firstSentences2)
        }
    }
}
