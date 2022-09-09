package com.won983212.mongle.presentation.component.calendar

import java.time.LocalDate

fun interface OnSelectionChangedListener {
    fun onSelectionChanged(selection: LocalDate)
}