package com.nibalk.tasky.core.data.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

fun Long.toLocalDateTime(): LocalDateTime {
    return Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun LocalDateTime.toLongDate(): Long {
    return this.atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

fun LocalDate.toLongDate(): Long {
    return this
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}
