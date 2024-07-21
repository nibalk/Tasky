package com.nibalk.tasky.agenda.domain.model

import java.time.LocalDateTime

sealed class AgendaItem(
    open val id: String?,
    open val title: String,
    open val description: String,
    open val startAt: LocalDateTime,
    open val remindAt: LocalDateTime,
) {
    data class Event(
        override val id: String?,
        override val title: String,
        override val description: String,
        override val startAt: LocalDateTime,
        override val remindAt: LocalDateTime,
        val endAt: LocalDateTime,
        val isHost: Boolean,
        val hostId: String,
        val attendees: List<EventAttendee> = emptyList(),
        val photos: List<EventPhoto> = emptyList(),
    ) : AgendaItem(id, title, description, startAt, remindAt)

    data class Task(
        override val id: String?,
        override val title: String,
        override val description: String,
        override val startAt: LocalDateTime,
        override val remindAt: LocalDateTime,
        val isDone: Boolean,
    ) : AgendaItem(id, title, description, startAt, remindAt)

    data class Reminder(
        override val id: String?,
        override val title: String,
        override val description: String,
        override val startAt: LocalDateTime,
        override val remindAt: LocalDateTime,
    ) : AgendaItem(id, title, description, startAt, remindAt)
}
