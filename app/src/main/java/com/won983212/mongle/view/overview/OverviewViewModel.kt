package com.won983212.mongle.view.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.won983212.mongle.common.util.asLiveData

class OverviewViewModel : ViewModel() {
    private val _keywords = MutableLiveData<List<Keyword>>(listOf())
    val keywords = Transformations.map(_keywords) { value ->
        value.ifEmpty {
            listOf(Keyword("비어있음"))
        }
    }

    private val _hasData = MutableLiveData(false)
    val hasData = _hasData.asLiveData()
}