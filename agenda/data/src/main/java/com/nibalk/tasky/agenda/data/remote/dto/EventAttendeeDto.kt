package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventAttendeeDto(
    val userId: String,
    val fullName: String,
    val email: String,
    val isGoing: Boolean,
    val remindAt: Long,
)
