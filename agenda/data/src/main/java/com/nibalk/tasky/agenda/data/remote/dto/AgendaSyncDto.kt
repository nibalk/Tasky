package com.nibalk.tasky.agenda.data.remote.dto

data class AgendaSyncDto(
    val deletedEventIds: List<String>,
    val deletedTaskIds: List<String>,
    val deletedReminderIds: List<String>
)
