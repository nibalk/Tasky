package com.nibalk.tasky.agenda.domain

import java.time.LocalDateTime

data class EventAttendee(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: LocalDateTime
)
