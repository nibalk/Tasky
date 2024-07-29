package com.nibalk.tasky.core.domain.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun LocalDate.getSurroundingDays(
    before: Int = 12,
    after: Int = 17
): List<LocalDate> {
    val startDate = this.minusDays(before.toLong())
    return (0 until (before + after))
        .map {
            startDate.plusDays(it.toLong())
        }
        .toList()
}

// Long -> LocalDate and LocalDateTime

fun Long.toLocalDate(): LocalDate {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

// LocalDate and LocalDateTime -> Long

fun LocalDate.toLongDate(): Long {
    return this
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun LocalDateTime.toLongDateTime(): Long {
    return this.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

// LocalDate - startOf and endOf in Millis

fun LocalDate.toStartOfDayMillis(): Long {
    return this
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun LocalDate.toEndOfDayMillis(): Long {
    return this
        .atTime(LocalTime.MAX)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
