package com.nibalk.tasky.agenda.data.remote.source

import com.nibalk.tasky.agenda.data.remote.api.TaskApi
import com.nibalk.tasky.agenda.data.remote.mapper.toAgendaItemTask
import com.nibalk.tasky.agenda.data.remote.mapper.toTaskDto
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.remote.RemoteTaskDataSource
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.map
import timber.log.Timber

class RetrofitRemoteTaskDataSource(
    private val taskApi: TaskApi
) : RemoteTaskDataSource {

    override suspend fun getTask(
        taskId: String
    ): Result<AgendaItem.Task?, DataError.Network> {
        val response = safeCall {
            taskApi.getTask(taskId)
        }
        return response.map { taskDto ->
            taskDto?.toAgendaItemTask()
        }
    }

    override suspend fun createTask(
        task: AgendaItem.Task
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            taskApi.createTask(task.toTaskDto())
        }
        Timber.d("[OfflineFirst-SaveItem] REMOTE | Creating TASK (%s)", task.id)
        return response.asEmptyDataResult()
    }

    override suspend fun updateTask(
        task: AgendaItem.Task
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            taskApi.updateTask(task.toTaskDto())
        }
        return response.asEmptyDataResult()
    }

    override suspend fun deleteTask(
        taskId: String
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            taskApi.deleteTask(taskId)
        }
        return response.asEmptyDataResult()
    }

}
