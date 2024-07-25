package com.nibalk.tasky.agenda.domain.source.remote

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result

interface RemoteTaskDataSource {
    suspend fun getTask(
        taskId: String
    ): Result<AgendaItem.Task?, DataError.Network>

    suspend fun createTask(
        task: AgendaItem.Task
    ): EmptyResult<DataError.Network>

    suspend fun updateTask(
        task: AgendaItem.Task
    ): EmptyResult<DataError.Network>

    suspend fun deleteTask(
        taskId: String
    ): EmptyResult<DataError.Network>
}
