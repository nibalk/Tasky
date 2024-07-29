package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.AgendaDeletedItemsDto
import com.nibalk.tasky.agenda.data.remote.dto.AgendaItemsDto
import com.nibalk.tasky.agenda.domain.model.AgendaDeletedItems
import com.nibalk.tasky.agenda.domain.model.AgendaItems

fun AgendaItems.toAgendaItemsDto(): AgendaItemsDto {
    return AgendaItemsDto(
        events = events.map { event ->
            event.toEventDto()
        },
        tasks = tasks.map { task ->
            task.toTaskDto()
        },
        reminders = reminders.map { event ->
            event.toReminderDto()
        }

    )
}

fun AgendaItemsDto.toAgendaItems(): AgendaItems {
    return AgendaItems(
        events = events.map { eventDto ->
            eventDto.toAgendaItemEvent()
        },
        tasks = tasks.map { taskDto ->
            taskDto.toAgendaItemTask()
        },
        reminders = reminders.map { reminderDto ->
            reminderDto.toAgendaItemReminder()
        },
    )
}

fun AgendaDeletedItems.toAgendaDeletedItemsDto(): AgendaDeletedItemsDto {
    return AgendaDeletedItemsDto(
        deletedEventIds = eventIds,
        deletedTaskIds = taskIds,
        deletedReminderIds = reminderIds,
    )
}

fun AgendaDeletedItemsDto.toAgendaDeletedItems(): AgendaDeletedItems {
    return AgendaDeletedItems(
        eventIds = deletedEventIds,
        taskIds = deletedTaskIds,
        reminderIds = deletedReminderIds,
    )
}
