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

// Convert from Long timestamp

fun Long.toLocalDate(): LocalDate {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.of("UTC"))
        .withZoneSameInstant(ZoneId.systemDefault())
        .toLocalDate()
}

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.of("UTC"))
        .withZoneSameInstant(ZoneId.systemDefault())
        .toLocalDateTime()
}

// Convert to Long timestamp

fun LocalDateTime.toEpochMillis(): Long {
    return this.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

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
