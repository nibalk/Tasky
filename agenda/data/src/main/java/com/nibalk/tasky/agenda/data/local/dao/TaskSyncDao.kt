package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nibalk.tasky.agenda.data.local.entity.TaskDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.TaskPendingSyncEntity

@Dao
interface TaskSyncDao {

    // Tasks created offline

    @Query("SELECT * FROM ${TaskPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${TaskPendingSyncEntity.USER_ID_KEY} = :userId")
    suspend fun getAllTaskPendingSyncEntities(userId: String): List<TaskPendingSyncEntity>

    @Query("SELECT * FROM ${TaskPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${TaskPendingSyncEntity.PRIMARY_KEY} = :taskId")
    suspend fun getTaskPendingSyncEntity(taskId: String): TaskPendingSyncEntity?

    @Upsert
    suspend fun upsertTaskPendingSyncEntity(entity: TaskPendingSyncEntity)

    @Query("DELETE FROM ${TaskPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${TaskPendingSyncEntity.PRIMARY_KEY} = :taskId")
    suspend fun deleteTaskPendingSyncEntity(taskId: String)


    // Tasks deleted offline

    @Query("SELECT * FROM ${TaskDeletionSyncEntity.TABLE_NAME} " +
        "WHERE ${TaskDeletionSyncEntity.PRIMARY_KEY} = :userId")
    suspend fun getAllTaskDeletionSyncEntities(userId: String): List<TaskDeletionSyncEntity>

    @Upsert
    suspend fun upsertTaskDeletionSyncEntity(entity: TaskDeletionSyncEntity)

    @Query("DELETE FROM ${TaskDeletionSyncEntity.TABLE_NAME} " +
        "WHERE ${TaskDeletionSyncEntity.PRIMARY_KEY} = :taskId")
    suspend fun deleteTaskDeletionSyncEntity(taskId: String)
}
