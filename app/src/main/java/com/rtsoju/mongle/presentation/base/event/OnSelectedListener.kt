package com.rtsoju.mongle.presentation.base.event

fun interface OnSelectedListener<T> {
    fun onSelected(value: T)
}