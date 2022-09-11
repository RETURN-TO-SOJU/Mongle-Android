package com.won983212.mongle

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

fun Date.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault())

fun Date.toLocalDate(): LocalDate =
    LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault()).toLocalDate()

fun LocalDateTime.toEpochMilli(): Long =
    atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun ofEpochMilli(epoch: Long): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(epoch), ZoneId.systemDefault())


class DatetimeFormats {
    companion object {
        /** yyyy.MM.dd */
        val DATE_DOT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        /** yyyy. MM */
        val MONTH_DOT_SPACE: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy. MM")

        /** yyyy.MM */
        val MONTH_DOT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM")

        /** yyyy-MM */
        val MONTH_SLASH: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM")

        /** a hh:mm */
        val TIME_12: DateTimeFormatter = DateTimeFormatter.ofPattern("a hh:mm")

        /** yyyy년 M월 d일 */
        val DATE_KR: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

        /** yyyy년 MM월 dd일 */
        val DATE_KR_FULL: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")

        /** M월 d일 EEEE*/
        val DATE_KR_WEEKDAY: DateTimeFormatter = DateTimeFormatter.ofPattern("M월 d일 EEEE")
    }
}