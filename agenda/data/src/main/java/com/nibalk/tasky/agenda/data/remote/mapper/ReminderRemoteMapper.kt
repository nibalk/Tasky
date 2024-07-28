package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.ReminderDto
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.data.utils.toLocalDateTime
import com.nibalk.tasky.core.data.utils.toLongDate

fun AgendaItem.Reminder.toReminderDto(): ReminderDto {
    return ReminderDto(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLongDate(),
        remindAt = remindAt.toLongDate(),
    )
}

fun ReminderDto.toAgendaItemReminder(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLocalDateTime(),
        remindAt = remindAt.toLocalDateTime(),
    )
}
