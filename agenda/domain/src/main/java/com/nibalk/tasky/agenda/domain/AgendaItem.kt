package com.nibalk.tasky.agenda.domain

import java.time.LocalDateTime

sealed class AgendaItem(
    val id: String,
    val title: String,
    val description: String,
    val time: LocalDateTime,
    val remindAt: LocalDateTime,
    val isDone: Boolean,
) {
    data class Event(
        val eventId: String,
        val eventTitle: String,
        val eventDescription: String,
        val eventFromDateTime: LocalDateTime,
        val eventToDateTime: LocalDateTime,
        val eventRemindAt: LocalDateTime,
        val eventIsDone: Boolean,
        val eventAttendees: List<EventAttendee>,
        val eventHostId: String,
        val eventIsHost: Boolean,
        val eventPhotos: List<EventPhoto>
    ) : AgendaItem(
        id = eventId,
        title = eventTitle,
        description = eventDescription,
        time = eventFromDateTime,
        remindAt = eventRemindAt,
        isDone = eventIsDone,
    )

    data class Task(
        val taskId: String,
        val taskTitle: String,
        val taskDescription: String,
        val taskDateTime: LocalDateTime,
        val taskRemindAt: LocalDateTime,
        val taskIsDone: Boolean
    ) : AgendaItem(
        id = taskId,
        title = taskTitle,
        description = taskDescription,
        time = taskDateTime,
        remindAt = taskRemindAt,
        isDone = taskIsDone,
    )

    data class Reminder(
        val reminderId: String,
        val reminderTitle: String,
        val reminderDescription: String,
        val reminderDateTime: LocalDateTime,
        val reminderRemindAt: LocalDateTime,
        val reminderIsDone: Boolean
    ) : AgendaItem(
        id = reminderId,
        title = reminderTitle,
        description = reminderDescription,
        time = reminderDateTime,
        remindAt = reminderRemindAt,
        isDone = reminderIsDone
    )
}
