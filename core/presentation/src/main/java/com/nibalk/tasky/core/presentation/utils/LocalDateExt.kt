package com.nibalk.tasky.core.presentation.utils

import java.time.Instant
import java.time.LocalDate
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

fun Long.toLocalDate(): LocalDate {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun LocalDate.toLongDate(): Long {
    return this
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
