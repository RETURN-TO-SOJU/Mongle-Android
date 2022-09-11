package com.won983212.mongle.presentation.view.favorite

import androidx.lifecycle.MutableLiveData
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.presentation.base.BaseViewModel
import java.time.YearMonth

class FavoriteViewModel : BaseViewModel() {

    private val _yearMonthList = MutableLiveData(
        listOf<YearMonth>(
            YearMonth.of(2022, 8),
            YearMonth.of(2022, 9),
            YearMonth.of(2022, 10),
            YearMonth.of(2022, 11),
            YearMonth.of(2022, 12)
        )
    )
    val yearMonthList = _yearMonthList.asLiveData()
}