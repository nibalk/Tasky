package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nibalk.tasky.agenda.data.local.entity.ReminderDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.ReminderPendingSyncEntity

@Dao
interface ReminderSyncDao {

    // Reminders created offline

    @Query("SELECT * FROM ${ReminderPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${ReminderPendingSyncEntity.USER_ID_KEY} = :userId")
    suspend fun getAllReminderPendingSyncEntities(userId: String): List<ReminderPendingSyncEntity>

    @Query("SELECT * FROM ${ReminderPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${ReminderPendingSyncEntity.PRIMARY_KEY} = :reminderId")
    suspend fun getReminderPendingSyncEntity(reminderId: String): ReminderPendingSyncEntity?

    @Upsert
    suspend fun upsertReminderPendingSyncEntity(entity: ReminderPendingSyncEntity)

    @Query("DELETE FROM ${ReminderPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${ReminderPendingSyncEntity.PRIMARY_KEY} = :reminderId")
    suspend fun deleteReminderPendingSyncEntity(reminderId: String)


    // Reminders deleted offline

    @Query("SELECT * FROM ${ReminderDeletionSyncEntity.TABLE_NAME} " +
        "WHERE ${ReminderDeletionSyncEntity.PRIMARY_KEY} = :userId")
    suspend fun getAllReminderDeletionSyncEntities(userId: String): List<ReminderDeletionSyncEntity>

    @Upsert
    suspend fun upsertReminderDeletionSyncEntity(entity: ReminderDeletionSyncEntity)

    @Query("DELETE FROM ${ReminderDeletionSyncEntity.TABLE_NAME} " +
        "WHERE ${ReminderDeletionSyncEntity.PRIMARY_KEY} = :reminderId")
    suspend fun deleteReminderDeletionSyncEntity(reminderId: String)
}
