package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.TaskEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.data.utils.toLocalDateTime
import com.nibalk.tasky.core.data.utils.toLongDate
import java.util.UUID

fun AgendaItem.Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id ?: UUID.randomUUID().toString(),
        title = title,
        description = description,
        startAt = startAt.toLongDate(),
        remindAt = remindAt.toLongDate(),
        isDone = isDone,
    )
}

fun TaskEntity.toAgendaItemTask(): AgendaItem.Task {
    return AgendaItem.Task(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLocalDateTime(),
        remindAt = remindAt.toLocalDateTime(),
        isDone = isDone,
    )
}
