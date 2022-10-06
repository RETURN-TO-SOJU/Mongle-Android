package com.won983212.mongle.util

fun generateFillZero(len: Int): String {
    val sb = StringBuilder()
    for (i in 1..len) {
        sb.append('0')
    }
    return sb.toString()
}