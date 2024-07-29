package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.ReminderEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.data.utils.toLocalDateTime
import com.nibalk.tasky.core.data.utils.toLongDate

fun AgendaItem.Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id.orEmpty(),
        title = title,
        description = description,
        startAt = startAt.toLongDate(),
        remindAt = remindAt.toLongDate(),
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
