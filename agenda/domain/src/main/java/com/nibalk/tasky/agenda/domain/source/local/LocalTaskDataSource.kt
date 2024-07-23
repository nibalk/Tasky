package com.nibalk.tasky.agenda.domain.source.local

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias TaskId = String

interface LocalTaskDataSource {
    suspend fun getAllTasks(): Flow<List<AgendaItem.Task>>
    suspend fun getTasksByDate(selectedDate: Long): Flow<List<AgendaItem.Task>>
    suspend fun getTaskById(taskId: String): AgendaItem.Task?

    suspend fun upsertTask(
        task: AgendaItem.Task
    ): Result<TaskId, DataError.Local>
    suspend fun upsertTasks(
        tasks: List<AgendaItem.Task>
    ): Result<List<TaskId>, DataError.Local>

    suspend fun deleteTask(taskId: String)
    suspend fun deleteAllTasks()
}
