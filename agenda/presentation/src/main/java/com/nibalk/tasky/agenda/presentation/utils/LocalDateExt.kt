package com.nibalk.tasky.agenda.presentation.utils

import java.time.LocalDate

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
