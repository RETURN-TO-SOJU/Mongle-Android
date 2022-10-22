package com.rtsoju.mongle.presentation.view.statistics

import androidx.annotation.IdRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.rtsoju.mongle.R
import com.rtsoju.mongle.presentation.base.BaseViewModel
import com.rtsoju.mongle.presentation.util.asLiveData
import java.time.LocalDate

class StatisticsViewModel : BaseViewModel() {

    private val _selectedTimeRange = MutableLiveData(R.id.radio_statistics_weekly)
    val selectedTimeRange = _selectedTimeRange.asLiveData()

    private val _selectedTime = MutableLiveData(LocalDate.now())
    val selectedTimeText = _selectedTime.map { formatSelectedTimeRange(it) }


    fun selectTimeRange(@IdRes id: Int) {
        _selectedTimeRange.postValue(id)
    }

    private fun formatSelectedTimeRange(date: LocalDate): String {
        // TODO Implementation
        return ""
    }
}