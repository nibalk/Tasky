package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgendaItemsDto (
    @SerialName("events") val events: List<EventDto>,
    @SerialName("tasks") val tasks: List<TaskDto>,
    @SerialName("reminders") val reminders: List<ReminderDto>
)
