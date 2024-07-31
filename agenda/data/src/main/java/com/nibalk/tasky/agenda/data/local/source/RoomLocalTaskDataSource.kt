package com.nibalk.tasky.agenda.data.local.source

import android.database.sqlite.SQLiteFullException
import com.nibalk.tasky.agenda.data.local.dao.TaskDao
import com.nibalk.tasky.agenda.data.local.mapper.toAgendaItemTask
import com.nibalk.tasky.agenda.data.local.mapper.toTaskEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.LocalTaskDataSource
import com.nibalk.tasky.agenda.domain.source.local.TaskId
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.toEndOfDayMillis
import com.nibalk.tasky.core.domain.util.toStartOfDayMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class RoomLocalTaskDataSource(
    private val taskDao: TaskDao
) : LocalTaskDataSource {

    override suspend fun getAllTasks(): Flow<List<AgendaItem.Task>> {
        return taskDao.getAllTasks().map { entities ->
            entities.map { entity ->
                entity.toAgendaItemTask()
            }
        }
    }

    override suspend fun getTasksByDate(selectedDate: LocalDate): Flow<List<AgendaItem.Task>> {
        return taskDao.getAllTasksByDate(
            selectedDate.toStartOfDayMillis(), selectedDate.toEndOfDayMillis()
        ).map { entities ->
            entities.map { entity ->
                entity.toAgendaItemTask()
            }
        }
    }

    override suspend fun getTaskById(taskId: String): AgendaItem.Task? {
        return taskDao.getTaskById(taskId)?.toAgendaItemTask()
    }

    override suspend fun upsertTask(task: AgendaItem.Task): Result<TaskId, DataError.Local> {
        return try {
            val entity = task.toTaskEntity()
            taskDao.upsertTask(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertTasks(tasks: List<AgendaItem.Task>): Result<List<TaskId>, DataError.Local> {
        return try {
            val entities = tasks.map { item ->
                item.toTaskEntity()
            }
            taskDao.updateTasks(entities)
            Result.Success(
                entities.map { entity ->
                    entity.id
                }
            )
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteTask(taskId: String) {
        taskDao.deleteTaskById(taskId)
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }
}
