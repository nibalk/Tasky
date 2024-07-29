package com.nibalk.tasky.agenda.domain.model

data class AgendaDeletedItems (
    val eventIds: List<String>,
    val taskIds: List<String>,
    val reminderIds: List<String>
)
