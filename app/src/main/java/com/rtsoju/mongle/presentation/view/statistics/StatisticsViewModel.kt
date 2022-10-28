package com.rtsoju.mongle.presentation.view.statistics

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.rtsoju.mongle.R
import com.rtsoju.mongle.domain.model.Emotion
import com.rtsoju.mongle.domain.model.StatisticsResult
import com.rtsoju.mongle.domain.usecase.statistics.GetMonthlyStatisticsUseCase
import com.rtsoju.mongle.domain.usecase.statistics.GetWeeklyStatisticsUseCase
import com.rtsoju.mongle.domain.usecase.statistics.GetYearlyStatisticsUseCase
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.TextResource
import com.rtsoju.mongle.presentation.util.asLiveData
import com.rtsoju.mongle.presentation.view.statistics.range.DateRange
import com.rtsoju.mongle.presentation.view.statistics.range.MonthRange
import com.rtsoju.mongle.presentation.view.statistics.range.WeekRange
import com.rtsoju.mongle.presentation.view.statistics.range.YearRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getWeeklyStatistics: GetWeeklyStatisticsUseCase,
    private val getMonthlyStatistics: GetMonthlyStatisticsUseCase,
    private val getYearlyStatistics: GetYearlyStatisticsUseCase
) : BaseViewModel() {

    private val _selectedDateRangeUnit = MutableLiveData(R.id.radio_statistics_weekly)

    /** Selected radio button ID of representing Time Range (Weekly, Monthly, Yearly) */
    val selectedDateRangeUnit = _selectedDateRangeUnit.asLiveData()

    private val _selectedDateRange =
        MutableLiveData<DateRange>(WeekRange.fromLocalDate(LocalDate.now()))
    val selectedDateRangeText = _selectedDateRange.map { it.toString() }

    private val _emotionCount = MutableLiveData<List<EmotionChartData>>(listOf())
    val emotionCount = _emotionCount.asLiveData()

    private val _scoreStatistics = MutableLiveData<List<StatisticsResult.Score>>(listOf())
    val scoreStatistics = _scoreStatistics.asLiveData()

    val overviewText = Transformations.map(_scoreStatistics) { scores ->
        var avgScore = 0f
        scores.mapNotNull { it.score }.run {
            forEach { avgScore += it }
            avgScore /= size
        }
        if (avgScore >= 70) {
            TextResource(R.string.statistics_overview_3)
        } else if (avgScore >= 35) {
            TextResource(R.string.statistics_overview_2)
        } else {
            TextResource(R.string.statistics_overview_1)
        }
    }


    fun selectDateRangeUnit(@IdRes id: Int) {
        val dateRange: DateRange = when (id) {
            R.id.radio_statistics_weekly -> {
                WeekRange.fromLocalDate(LocalDate.now())
            }
            R.id.radio_statistics_monthly -> {
                MonthRange.fromLocalDate(LocalDate.now())
            }
            R.id.radio_statistics_yearly -> {
                YearRange.fromLocalDate(LocalDate.now())
            }
            else -> {
                throw java.lang.IllegalArgumentException("Unknown radio button ID of date range: $id")
            }
        }

        _selectedDateRange.value = dateRange
        _selectedDateRangeUnit.value = id
        updateStatistics()
    }

    fun goNextDateRange() {
        _selectedDateRange.value = _selectedDateRange.value?.next()
        updateStatistics()
    }

    fun goPrevDateRange() {
        _selectedDateRange.value = _selectedDateRange.value?.prev()
        updateStatistics()
    }

    fun updateStatistics() {
        val dateRange = _selectedDateRange.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val statistics = when (dateRange) {
                is WeekRange -> {
                    getWeeklyStatistics(dateRange.year, dateRange.month, dateRange.week)
                }
                is MonthRange -> {
                    getMonthlyStatistics(dateRange.year, dateRange.month)
                }
                is YearRange -> {
                    getYearlyStatistics(dateRange.year)
                }
            }
            statistics.onSuccess { result ->
                val emotionCounts = mutableListOf<EmotionChartData>()
                val totalEmotions = result.emotionCount.sumOf { it }
                for (emotion in Emotion.values()) {
                    val count = result.emotionCount[emotion.ordinal]
                    emotionCounts.add(
                        EmotionChartData(emotion, count, count.toFloat() / totalEmotions * 100)
                    )
                }
                _emotionCount.postValue(emotionCounts)
                _scoreStatistics.postValue(result.scores)
            }
        }
    }
}