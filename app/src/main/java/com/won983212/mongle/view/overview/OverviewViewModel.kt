package com.won983212.mongle.view.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.won983212.mongle.common.util.asLiveData
import com.won983212.mongle.view.overview.Keyword

class OverviewViewModel : ViewModel() {
    private val _keywords = MutableLiveData(
        listOf(
            Keyword("학교"),
            Keyword("디자인"),
            Keyword("자바")
        )
    )
    val keywords = _keywords.asLiveData()
}