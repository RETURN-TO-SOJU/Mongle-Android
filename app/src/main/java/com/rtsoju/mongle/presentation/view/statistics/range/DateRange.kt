package com.rtsoju.mongle.presentation.view.statistics.range

import java.time.LocalDate
import java.util.*

/**
 * 날짜 범위를 나타낸다. year만 명시하면 그 연도 전체(year.01.01 ~ year.12.31)을 나타낸다.
 * month, week 순으로 추가 명시하면 더 짧은 범위를 나타낼 수 있다. (month: 월, week: 주 단위 범위)
 * @param year 년. 연도만 명시하면 그 연도의 모든 일들을 포함한 날짜를 표현한다.
 * @param month 월. 년+월을 명시하면 해당 월의 모든 일들을 포함한 날짜를 표현한다. 범위는 1~12
 * @param week 주. 년+월+주를 명시하면 해당 주의 모든 일들을 포함한 날짜를 표현한다. 범위는 1~5이지만, 월에 따라서 다르다.
 */
sealed interface DateRange {
    /**
     * 다음 날짜로 단위만큼 이동한 새 객체를 반환한다.
     * 예를 들어 [DateRange.Year]의 경우 [next]시 다음 연도를 표현하는 객체를 생성한다.
     * [DateRange.Month]는 다음 달(month), [DateRange.Week]는 다음 주(week) 객체를 생성하여 반환한다.
     * 2022년 10월의 경우 다음 달은 2022년 11월.
     */
    fun next(): DateRange

    /**
     * 이전 날짜로 단위만큼 이동한 새 객체를 반환한다.
     * 예를 들어 [DateRange.Year]의 경우 [prev]시 이전 연도를 표현하는 객체를 생성한다.
     * [DateRange.Month]는 이전 달(month), [DateRange.Week]는 이전 주(week) 객체를 생성하여 반환한다.
     * 2022년 10월의 경우 이전 달은 2022년 9월.
     */
    fun prev(): DateRange

    /**
     * 범위의 처음 날짜를 반환한다.
     * 예를 들어 [DateRange.Year]의 경우 YYYY.01.01이 반환된다.
     * [DateRange.Month]는 달(month)의 처음 일, [DateRange.Week]는 주(week)의 처음 일 반환한다.
     * 2022년 10월의 경우 [getRangeStart]은 2022.10.01이 된다.
     */
    fun getRangeStart(): LocalDate

    /**
     * 범위의 끝 날짜를 반환한다.
     * 예를 들어 [DateRange.Year]의 경우 YYYY.12.31이 반환된다.
     * [DateRange.Month]는 달(month)의 끝 일, [DateRange.Week]는 주(week)의 끝 일 반환한다.
     * 2022년 10월의 경우 [getRangeEnd]은 2022.10.31이 된다.
     */
    fun getRangeEnd(): LocalDate
}