package com.rtsoju.mongle.data.repository

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.rtsoju.mongle.data.repository.CalendarRepositoryImpl
import com.rtsoju.mongle.data.db.AppDatabase
import com.rtsoju.mongle.data.db.dao.CalendarDao
import com.rtsoju.mongle.data.mapper.toDomainModel
import com.rtsoju.mongle.data.source.local.LocalCalendarDataSource
import com.rtsoju.mongle.data.source.remote.RemoteCalendarDataSource
import com.rtsoju.mongle.data.source.remote.dto.request.DiaryRequest
import com.rtsoju.mongle.data.source.remote.dto.response.CalendarDayDetailResponse
import com.rtsoju.mongle.data.source.remote.dto.response.CalendarDayPreviewResponse
import com.rtsoju.mongle.domain.model.CachePolicy
import com.rtsoju.mongle.domain.model.CalendarDayDetail
import com.rtsoju.mongle.domain.model.CalendarDayPreview
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.repository.CalendarRepository
import com.rtsoju.mongle.exception.NoResultException
import com.rtsoju.mongle.util.*
import com.rtsoju.mongle.mock.MockCalendarApi
import com.rtsoju.mongle.util.*
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
internal class CalendarRepositoryTest {
    private lateinit var database: AppDatabase
    private lateinit var calendarDao: CalendarDao
    private lateinit var localDataSource: LocalCalendarDataSource
    private lateinit var remoteDataSource: RemoteCalendarDataSource
    private lateinit var repository: CalendarRepository
    private lateinit var mockApi: MockCalendarApi

    @Before
    fun before() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        calendarDao = database.calenderDao()
        localDataSource = LocalCalendarDataSource(database)
        mockApi = MockCalendarApi()
        remoteDataSource = RemoteCalendarDataSource(mockApi)
        repository = CalendarRepositoryImpl(localDataSource, remoteDataSource)
    }

    @After
    fun after() {
        database.close()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateDiary(): Unit = runTest {
        val date = LocalDate.now()

        repository.updateDiary(date, "Hello, world!")
        testSuccessResult(localDataSource.getCalendarDayDetail(date)) {
            assertThat(it.diary).isEqualTo("Hello, world!")
        }
        testSuccessResult(remoteDataSource.getCalendarDayDetail(date)) {
            assertThat(it.diary).isEqualTo("Hello, world!")
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun updateEmotion(): Unit = runTest {
        val date = LocalDate.now()

        repository.updateEmotion(date, Emotion.SAD)
        testSuccessResult(localDataSource.getCalendarDayDetail(date)) {
            assertThat(it.emotion).isEqualTo(Emotion.SAD)
        }
        testSuccessResult(remoteDataSource.getCalendarDayDetail(date)) {
            assertThat(it.emotion).isEqualTo(Emotion.SAD)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun get_calendar_day_preview(
        cachePolicy: CachePolicy,
        localExpectedValueGenerator: (YearMonth, YearMonth) -> List<CalendarDayPreviewResponse>,
        refreshLocalResult: Boolean,
        onNext: (
            localResult: Result<List<CalendarDayPreview>>,
            expected: List<CalendarDayPreview>
        ) -> Unit
    ): Unit = runTest {
        val start = YearMonth.of(2022, 8)
        val end = YearMonth.of(2022, 9)
        val expected = localExpectedValueGenerator(start, end).map {
            CalendarDayPreview(it.date, it.emotion, it.subjectList)
        }

        mockApi.calendarDayMetadataProducer = ::generateCalendarPreviewResponse1
        repository.getCalendarDayPreview(start, end, cachePolicy)
        var localResult = localDataSource.getCalendarDayPreview(start, end)

        mockApi.calendarDayMetadataProducer = ::generateCalendarPreviewResponse2
        val result = repository.getCalendarDayPreview(start, end, cachePolicy)
        if (refreshLocalResult) {
            localResult = localDataSource.getCalendarDayPreview(start, end)
        }

        testSuccessResult(result) { data ->
            assertThat(data).containsExactlyElementsIn(expected)
            onNext(localResult, expected)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_preview_once(): Unit = runTest {
        get_calendar_day_preview(
            CachePolicy.ONCE,
            ::generateCalendarPreviewResponse1,
            true
        ) { localResult, expected ->
            testSuccessResult(localResult) {
                assertThat(it).containsExactlyElementsIn(expected)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_preview_get_or_cache(): Unit = runTest {
        get_calendar_day_preview(
            CachePolicy.GET_OR_FETCH,
            ::generateCalendarPreviewResponse1,
            false
        ) { localResult, expected ->
            testSuccessResult(localResult) {
                assertThat(it).containsExactlyElementsIn(expected)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_preview_never(): Unit = runTest {
        get_calendar_day_preview(
            CachePolicy.NEVER,
            ::generateCalendarPreviewResponse2,
            false
        ) { localResult, _ ->
            testFailedResult(localResult) {
                assertThat(it).isInstanceOf(NoResultException::class.java)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_preview_refresh(): Unit = runTest {
        get_calendar_day_preview(
            CachePolicy.REFRESH,
            ::generateCalendarPreviewResponse2,
            true
        ) { localResult, expected ->
            testSuccessResult(localResult) {
                assertThat(it).containsExactlyElementsIn(expected)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun get_calendar_day_detail(
        cachePolicy: CachePolicy,
        expectValueGenerator: (LocalDate, String, Emotion?) -> CalendarDayDetailResponse,
        refreshLocalResult: Boolean,
        onNext: (
            localResult: Result<CalendarDayDetail>,
            expected: CalendarDayDetail
        ) -> Unit
    ): Unit = runTest {
        val date = LocalDate.of(2022, 8, 3)
        val expected = expectValueGenerator(date, "test", Emotion.HAPPY).toDomainModel(date)

        mockApi.updateDiary(0, "", "", DiaryRequest("test"))
        mockApi.updateEmotion(0, "", "", Emotion.HAPPY.name)
        mockApi.calendarDayDetailProducer = ::generateCalendarDetailResponse1

        repository.getCalendarDayDetail(date, cachePolicy)
        var localResult = localDataSource.getCalendarDayDetail(date)

        mockApi.calendarDayDetailProducer = ::generateCalendarDetailResponse2
        val result = repository.getCalendarDayDetail(date, cachePolicy)
        if (refreshLocalResult) {
            localResult = localDataSource.getCalendarDayDetail(date)
        }

        testSuccessResult(result) { data ->
            assertThat(data).isEqualTo(expected)
            onNext(localResult, expected)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_detail_once(): Unit = runTest {
        get_calendar_day_detail(
            CachePolicy.ONCE,
            ::generateCalendarDetailResponse1,
            true
        ) { localResult, expected ->
            testSuccessResult(localResult) {
                assertThat(it).isEqualTo(expected)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_detail_get_or_cache(): Unit = runTest {
        get_calendar_day_detail(
            CachePolicy.GET_OR_FETCH,
            ::generateCalendarDetailResponse1,
            false
        ) { localResult, expected ->
            testSuccessResult(localResult) {
                assertThat(it).isEqualTo(expected)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_detail_never(): Unit = runTest {
        get_calendar_day_detail(
            CachePolicy.NEVER,
            ::generateCalendarDetailResponse2,
            false
        ) { localResult, _ ->
            testFailedResult(localResult) {
                assertThat(it).isInstanceOf(NoResultException::class.java)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun get_calendar_day_detail_refresh(): Unit = runTest {
        get_calendar_day_detail(
            CachePolicy.REFRESH,
            ::generateCalendarDetailResponse2,
            true
        ) { localResult, expected ->
            testSuccessResult(localResult) {
                assertThat(it).isEqualTo(expected)
            }
        }
    }
}