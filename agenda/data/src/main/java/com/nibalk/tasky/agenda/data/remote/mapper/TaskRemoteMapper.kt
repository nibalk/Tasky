package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.TaskDto
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.toLocalDateTime
import com.nibalk.tasky.core.domain.util.toLongDateTime

fun AgendaItem.Task.toTaskDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLongDateTime(),
        remindAt = remindAt.toLongDateTime(),
        isDone = isDone,
    )
}

fun TaskDto.toAgendaItemTask(): AgendaItem.Task {
    return AgendaItem.Task(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLocalDateTime(),
        remindAt = remindAt.toLocalDateTime(),
        isDone = isDone,
    )
}
