package com.nibalk.tasky.agenda.domain.model

data class SyncPendingEvent(
    val userId: String,
    val event: AgendaItem.Event,
    val localPhotos: List<ByteArray>,
)

data class SyncPendingReminder(
    val userId: String,
    val reminder: AgendaItem.Reminder,
    val localPhotos: List<ByteArray>,
)

data class SyncPendingTask(
    val userId: String,
    val task: AgendaItem.Task,
    val localPhotos: List<ByteArray>,
)

data class SyncDeletionAgendaItem(
    val userId: String,
    val agendaId: String,
)
