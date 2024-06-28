package com.nibalk.tasky.agenda.presentation.utils

import java.time.LocalDate

fun LocalDate.getSurroundingDays(before: Int = 12, after: Int = 17): List<LocalDate> {
    val days = mutableListOf<LocalDate>()
    // Add days before
    for (i in before downTo 1) {
        days.add(this.minusDays(i.toLong()))
    }
    // Add current day
    days.add(this)
    // Add days after
    for (i in 1..after) {
        days.add(this.plusDays(i.toLong()))
    }
    return days
}
