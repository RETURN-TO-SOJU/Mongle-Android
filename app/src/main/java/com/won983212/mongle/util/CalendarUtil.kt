package com.won983212.mongle.util

import org.threeten.bp.*
import org.threeten.bp.temporal.ChronoField

fun LocalDate.withFirstDayOfMonth(): LocalDate {
    return this.withDayOfMonth(1)
}

val LocalDate.epochSeconds: Long
    get() = this.toEpochDay()

fun createDateTimeFromEpoch(epochSeconds: Long): LocalDate {
    return LocalDate.ofEpochDay(epochSeconds)
}

/**
 * 선택된 날짜에 해당하는 월간 달력을 반환한다.
 */
fun LocalDate.getMonthList(): List<LocalDate> {
    val list = mutableListOf<LocalDate>()

    val date = this.withDayOfMonth(1)
    val prev = getPrevOffSet(date)

    val startValue = date.minusDays(prev.toLong())

    for (i in 0 until 42) {
        list.add(startValue.plusDays(i.toLong()))
    }

    return list
}

/**
 * 해당 calendar 의 이전 달의 일 갯수를 반환한다.
 */
private fun getPrevOffSet(dateTime: LocalDate): Int {
    var prevMonthTailOffset = dateTime.dayOfWeek.value

    if (prevMonthTailOffset >= 7) prevMonthTailOffset %= 7

    return prevMonthTailOffset
}

/**
 * 같은 달인지 체크
 */
fun LocalDate.isSameMonth(second: LocalDate): Boolean =
    this.year == second.year && this.month == second.month