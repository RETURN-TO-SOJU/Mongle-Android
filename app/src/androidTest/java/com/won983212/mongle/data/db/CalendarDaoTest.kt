package com.won983212.mongle.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.won983212.mongle.domain.model.Emotion
import com.won983212.mongle.util.generateCalendarDayEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
internal class CalendarDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: CalendarDao

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.calenderDao()
    }

    @After
    fun after() {
        database.close()
    }

    private suspend fun insertWeeklyTestEntity(date: LocalDate) {
        for (i in 1..10) {
            val data = generateCalendarDayEntity(date.plusDays((i * 7).toLong()))
            dao.insertCalendarDay(data)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day() = runTest {
        val date = LocalDate.now()
        val data1 = generateCalendarDayEntity(date)
        val data2 = generateCalendarDayEntity(date.plusDays(1))

        dao.insertCalendarDay(data1)
        dao.insertCalendarDay(data2)

        val result1 = dao.getCalendarDay(date)?.day
        assertThat(result1 == data1).isEqualTo(true)

        val result2 = dao.getCalendarDay(date.plusDays(1))?.day
        assertThat(result2 == data2).isEqualTo(true)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_preview() = runTest {
        val date = LocalDate.of(2022, 7, 26)
        val outBoundData = generateCalendarDayEntity(date)
        dao.insertCalendarDay(outBoundData)

        insertWeeklyTestEntity(date)

        val from = LocalDate.of(2022, 8, 1).toEpochDay()
        val to = LocalDate.of(2022, 8, 31).toEpochDay()
        val result = dao.getCalendarDayPreview(from, to)
        assertThat(result.size).isEqualTo(5)
        assertThat(result).doesNotContain(outBoundData)
    }

    private suspend fun initializeUpdateEnv(): LocalDate {
        val date = LocalDate.now()

        insertWeeklyTestEntity(date)

        val targetDate = date.plusDays(10)
        val targetValue = generateCalendarDayEntity(targetDate)
        dao.insertCalendarDay(targetValue)

        return targetDate
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_calendar_day_preview() = runTest {
        val targetDate = initializeUpdateEnv()
        dao.updateCalendarDayPreview(targetDate, Emotion.HAPPY, listOf("HI"))

        val result = dao.getCalendarDay(targetDate)?.day
        assertThat(result?.keywords).isEqualTo(listOf("HI"))
        assertThat(result?.emotion).isEqualTo(Emotion.HAPPY)
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_calendar_day_detail() = runTest {
        val targetDate = initializeUpdateEnv()
        dao.updateCalendarDayDetail(targetDate, Emotion.HAPPY, "zz", "zzzzz")

        val result = dao.getCalendarDay(targetDate)?.day
        assertThat(result?.emotion).isEqualTo(Emotion.HAPPY)
        assertThat(result?.diary).isEqualTo("zz")
        assertThat(result?.diaryFeedback).isEqualTo("zzzzz")
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_calendar_day_detail_null_emotion() = runTest {
        val targetDate = initializeUpdateEnv()
        dao.updateCalendarDayDetail(targetDate, null, "diary", "feedback")

        val result = dao.getCalendarDay(targetDate)?.day
        assertThat(result?.emotion).isEqualTo(null)
        assertThat(result?.diary).isEqualTo("diary")
        assertThat(result?.diaryFeedback).isEqualTo("feedback")
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_diary() = runTest {
        val targetDate = initializeUpdateEnv()
        dao.updateDiary(targetDate, "Hello")

        val result = dao.getCalendarDay(targetDate)?.day
        assertThat(result?.diary).isEqualTo("Hello")
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun update_emotion() = runTest {
        val targetDate = initializeUpdateEnv()
        dao.updateEmotion(targetDate, Emotion.TIRED)

        val result = dao.getCalendarDay(targetDate)?.day
        assertThat(result?.emotion).isEqualTo(Emotion.TIRED)
    }
}
