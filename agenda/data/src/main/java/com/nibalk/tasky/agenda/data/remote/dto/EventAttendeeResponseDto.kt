package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventAttendeeResponseDto(
    val doesUserExist: Boolean,
    val attendee: EventAttendeeDto
)
