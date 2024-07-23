package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String?,
    val title: String,
    val description: String,
    @SerialName("from") val startAt: Long,
    val remindAt: Long,
    @SerialName("to") val endAt: Long,
    @SerialName("isUserEventCreator") val isHost: Boolean,
    @SerialName("host") val hostId: String,
    val attendees: List<EventAttendeeDto> = emptyList(),
    val photos: List<EventPhotoDto> = emptyList(),
)
