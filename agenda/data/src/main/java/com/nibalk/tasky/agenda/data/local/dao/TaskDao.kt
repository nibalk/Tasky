package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nibalk.tasky.agenda.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Upsert
    suspend fun upsertTask(task: TaskEntity)

    @Upsert
    suspend fun updateTasks(tasks: List<TaskEntity>)

    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME} WHERE ${TaskEntity.PRIMARY_KEY} = :id")
    suspend fun getTask(id: String): TaskEntity?

    @Query("SELECT * FROM ${TaskEntity.TABLE_NAME}")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM ${TaskEntity.TABLE_NAME}")
    suspend fun deleteAllTasks()
}
