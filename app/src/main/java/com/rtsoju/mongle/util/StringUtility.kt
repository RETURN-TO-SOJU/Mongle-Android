package com.rtsoju.mongle.util

fun generateFillZero(len: Int): String {
    val sb = StringBuilder()
    for (i in 1..len) {
        sb.append('0')
    }
    return sb.toString()
}