package com.nibalk.tasky.agenda.domain.model

import java.time.LocalDateTime

data class EventAttendee(
    val eventId: String,
    val userId: String,
    val fullName: String,
    val email: String,
    val isGoing: Boolean,
    val remindAt: LocalDateTime
)
