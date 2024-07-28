package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgendaDeletedItemsDto (
    @SerialName("deletedEventIds") val deletedEventIds: List<String>,
    @SerialName("deletedTaskIds") val deletedTaskIds: List<String>,
    @SerialName("deletedReminderIds") val deletedReminderIds: List<String>
)
