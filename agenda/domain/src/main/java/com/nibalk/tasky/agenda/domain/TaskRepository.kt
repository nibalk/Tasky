package com.nibalk.tasky.agenda.domain

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

interface TaskRepository {
    suspend fun deleteTask(taskId: String)
    suspend fun getTask(taskId: String): AgendaItem.Task?
    suspend fun createTask(task: AgendaItem.Task): EmptyResult<DataError>
    suspend fun updateTask(task: AgendaItem.Task): EmptyResult<DataError>
}
