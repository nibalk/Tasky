package com.nibalk.tasky.test.mock

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDateTime

object AgendaSampleData {

    // Events
    val event1 = AgendaItem.Event(
        id = "E01",
        title = "Tasky Event - Attend 2024 Google I/O 1",
        description = "Attend Google I/O event to learn what is new in Android",
        startAt = LocalDateTime.now(),
        remindAt = LocalDateTime.now(),
        endAt = LocalDateTime.now(),
        hostId = "",
        isHost = false,
    )
    val event2 = AgendaItem.Event(
        id = "E02",
        title = "Tasky Event - Attend 2024 Google I/O 2",
        description = "Attend Google I/O event to learn what is new in Android",
        startAt = LocalDateTime.now().plusDays(1),
        remindAt = LocalDateTime.now(),
        endAt = LocalDateTime.now(),
        hostId = "",
        isHost = false,
    )
    val event3 = AgendaItem.Event(
        id = "E03",
        title = "Tasky Event - Attend 2024 Google I/O 3",
        description = "Attend Google I/O event to learn what is new in Android",
        startAt = LocalDateTime.now().plusDays(2),
        remindAt = LocalDateTime.now(),
        endAt = LocalDateTime.now(),
        hostId = "",
        isHost = false,
    )
    val event4 = AgendaItem.Event(
        id = "E04",
        title = "Tasky Event - Attend 2024 Google I/O 4",
        description = "Attend Google I/O event to learn what is new in Android",
        startAt = LocalDateTime.now().plusDays(2),
        remindAt = LocalDateTime.now(),
        endAt = LocalDateTime.now(),
        hostId = "",
        isHost = false,
    )
    val event5 = AgendaItem.Event(
        id = "E05",
        title = "Tasky Event - Attend 2024 Google I/O 5",
        description = "Attend Google I/O event to learn what is new in Android",
        startAt = LocalDateTime.now().plusHours(7),
        remindAt = LocalDateTime.now(),
        endAt = LocalDateTime.now(),
        hostId = "",
        isHost = false,
    )

    // Tasks
    val task1 = AgendaItem.Task(
        id = "T01",
        title = "Tasky Project - Complete Auth Feature 1",
        description = "Completing the Auth feature including the Login and Register flows",
        startAt = LocalDateTime.now(),
        remindAt = LocalDateTime.now(),
        isDone = true
    )
    val task2 = AgendaItem.Task(
        id = "T02",
        title = "Tasky Project - Complete Auth Feature 2",
        description = "Completing the Auth feature including the Login and Register flows",
        startAt = LocalDateTime.now().plusDays(3),
        remindAt = LocalDateTime.now(),
        isDone = true
    )
    val task3 = AgendaItem.Task(
        id = "T03",
        title = "Tasky Project - Complete Auth Feature 2",
        description = "Completing the Auth feature including the Login and Register flows",
        startAt = LocalDateTime.now().plusDays(3),
        remindAt = LocalDateTime.now(),
        isDone = true
    )
    val task4 = AgendaItem.Task(
        id = "T04",
        title = "Tasky Project - Complete Auth Feature 4",
        description = "Completing the Auth feature including the Login and Register flows",
        startAt = LocalDateTime.now().plusDays(4),
        remindAt = LocalDateTime.now(),
        isDone = true
    )
    val task5 = AgendaItem.Task(
        id = "T05",
        title = "Tasky Project - Complete Auth Feature 5",
        description = "Completing the Auth feature including the Login and Register flows",
        startAt = LocalDateTime.now().plusHours(9),
        remindAt = LocalDateTime.now(),
        isDone = false
    )

    // Reminders
    val reminder1 =  AgendaItem.Reminder(
        id = "R01",
        title = "Tasky Reminder - Weekly call 1",
        description = "Bi-weekly call",
        startAt = LocalDateTime.now().plusHours(2),
        remindAt = LocalDateTime.now(),
    )
    val reminder2 = AgendaItem.Reminder(
        id = "R02",
        title = "Tasky Reminder - Weekly call 2",
        description = "Bi-weekly call",
        startAt = LocalDateTime.now().plusHours(5),
        remindAt = LocalDateTime.now(),
    )
    val reminder3 = AgendaItem.Reminder(
        id = "R03",
        title = "Tasky Reminder - Weekly call 3",
        description = "Bi-weekly call",
        startAt = LocalDateTime.now().plusDays(1),
        remindAt = LocalDateTime.now(),
    )
    val reminder4 = AgendaItem.Reminder(
        id = "R04",
        title = "Tasky Reminder - Weekly call 4",
        description = "Bi-weekly call",
        startAt = LocalDateTime.now().plusDays(1).plusHours(3),
        remindAt = LocalDateTime.now(),
    )
    val reminder5 =  AgendaItem.Reminder(
        id = "R05",
        title = "Tasky Reminder - Weekly call 5",
        description = "Bi-weekly call",
        startAt = LocalDateTime.now().plusDays(2).plusHours(2),
        remindAt = LocalDateTime.now(),
    )

    // Agenda Lists
    val allAgendas: List<AgendaItem> = listOf(
        event1, event2, event3, event4, event5,
        task1, task2, task3, task4, task5,
        reminder1, reminder2, reminder3, reminder4, reminder5,
    )

}
