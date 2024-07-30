package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias AttendeeId = String

@Serializable
data class EventRequestDto(
    @SerialName("id") val id: String?,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("from") val startAt: Long,
    @SerialName("remindAt") val remindAt: Long,
    @SerialName("to") val endAt: Long,
    @SerialName("attendeeIds") @Required val attendeeIds: List<AttendeeId> = emptyList(),
)
