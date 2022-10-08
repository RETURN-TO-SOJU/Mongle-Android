package com.won983212.mongle.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.won983212.mongle.data.source.local.entity.CalendarDayEntity
import com.won983212.mongle.domain.model.Emotion
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

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day() = runTest {
        val date = LocalDate.now()
        val data1 = CalendarDayEntity(
            date,
            Emotion.ANGRY,
            listOf("Hello", "Nice", "안녕"),
            "하이",
            "좋은 하루였네요."
        )
        val data2 = CalendarDayEntity(
            date.plusDays(1),
            null,
            listOf(""),
            "",
            ""
        )

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
        val outBoundData = CalendarDayEntity(
            date,
            Emotion.SAD,
            listOf("Hello"),
            "rrr",
            "aaaaaaa"
        )
        dao.insertCalendarDay(outBoundData)

        for (i in 1..10) {
            val data = CalendarDayEntity(
                date.plusDays((i * 7).toLong()),
                Emotion.ANGRY,
                listOf("Hello", "Nice", "안녕"),
                "하이",
                "좋은 하루였네요."
            )
            dao.insertCalendarDay(data)
        }

        val from = LocalDate.of(2022, 8, 1).toEpochDay()
        val to = LocalDate.of(2022, 8, 31).toEpochDay()
        val result = dao.getCalendarDayPreview(from, to)
        assertThat(result.size).isEqualTo(5)
        assertThat(result).doesNotContain(outBoundData)
    }
}
