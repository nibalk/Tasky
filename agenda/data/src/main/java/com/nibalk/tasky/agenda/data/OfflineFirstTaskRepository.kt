package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.TaskRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.LocalTaskDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteTaskDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onError

class OfflineFirstTaskRepository(
    private val localDataSource: LocalTaskDataSource,
    private val remoteDataSource: RemoteTaskDataSource
) : TaskRepository {

    override suspend fun deleteTask(taskId: String) {
        localDataSource.deleteTask(taskId)
    }

    override suspend fun getTask(taskId: String): AgendaItem.Task? {
        return localDataSource.getTaskById(taskId)
    }

    override suspend fun createTask(task: AgendaItem.Task): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(task)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }
        val remoteResult = remoteDataSource.createTask(task)
        return remoteResult.asEmptyDataResult()
    }

    override suspend fun updateTask(task: AgendaItem.Task): EmptyResult<DataError> {
        val localResult = localDataSource.upsertTask(task)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }

        val remoteResult = remoteDataSource.updateTask(task)
        return remoteResult.asEmptyDataResult()
    }
}
