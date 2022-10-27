package com.rtsoju.mongle.presentation.view.statistics

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.rtsoju.mongle.R
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData
import java.time.LocalDate

class StatisticsViewModel : BaseViewModel() {

    private val _selectedDateRangeUnit = MutableLiveData(R.id.radio_statistics_weekly)

    /** Selected radio button ID of representing Time Range (Weekly, Monthly, Yearly) */
    val selectedDateRangeUnit = _selectedDateRangeUnit.asLiveData()

    private val _selectedDateRange = MutableLiveData(DateRange.fromLocalDate(LocalDate.now()))
    val selectedDateRangeText = _selectedDateRange.map { it.toString() }


    fun selectDateRangeUnit(@IdRes id: Int) {
        _selectedDateRangeUnit.postValue(id)
    }
}