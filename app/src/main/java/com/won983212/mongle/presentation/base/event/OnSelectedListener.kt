package com.won983212.mongle.presentation.base.event

fun interface OnSelectedListener<T> {
    fun onSelected(value: T)
}