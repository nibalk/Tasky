package com.nibalk.tasky.agenda.data.remote.dto

data class AgendaDto(
    val events: List<EventDto>,
    val tasks: List<TaskDto>,
    val reminders: List<ReminderDto>
)
