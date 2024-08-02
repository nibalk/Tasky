package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.ReminderEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.toLocalDateTime
import com.nibalk.tasky.core.domain.util.toEpochMillis

fun AgendaItem.Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id.orEmpty(),
        title = title,
        description = description,
        startAt = startAt.toEpochMillis(),
        remindAt = remindAt.toEpochMillis(),
    )
}

fun ReminderEntity.toAgendaItemReminder(): AgendaItem.Reminder {
    return AgendaItem.Reminder(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLocalDateTime(),
        remindAt = remindAt.toLocalDateTime(),
    )
}
