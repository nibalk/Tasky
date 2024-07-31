package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.TaskEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.toLocalDateTime
import com.nibalk.tasky.core.domain.util.toLongDateTime

fun AgendaItem.Task.toTaskEntity(): TaskEntity {
    return TaskEntity(
        id = id.orEmpty(),
        title = title,
        description = description,
        startAt = startAt.toLongDateTime(),
        remindAt = remindAt.toLongDateTime(),
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
