package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.nibalk.tasky.agenda.data.local.entity.EventDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.EventPendingSyncEntity

@Dao
interface EventSyncDao {

    // Events created offline

    @Query("SELECT * FROM ${EventPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${EventPendingSyncEntity.USER_ID_KEY} = :userId")
    suspend fun getAllEventPendingSyncEntities(userId: String): List<EventPendingSyncEntity>

    @Query("SELECT * FROM ${EventPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${EventPendingSyncEntity.PRIMARY_KEY} = :eventId")
    suspend fun getEventPendingSyncEntity(eventId: String): EventPendingSyncEntity?

    @Upsert
    suspend fun upsertEventPendingSyncEntity(entity: EventPendingSyncEntity)

    @Query("DELETE FROM ${EventPendingSyncEntity.TABLE_NAME} " +
        "WHERE ${EventPendingSyncEntity.PRIMARY_KEY} = :eventId")
    suspend fun deleteEventPendingSyncEntity(eventId: String)


    // Events deleted offline

    @Query("SELECT * FROM ${EventDeletionSyncEntity.TABLE_NAME} " +
        "WHERE ${EventDeletionSyncEntity.PRIMARY_KEY} = :userId")
    suspend fun getAllEventDeletionSyncEntities(userId: String): List<EventDeletionSyncEntity>

    @Upsert
    suspend fun upsertEventDeletionSyncEntity(entity: EventDeletionSyncEntity)

    @Query("DELETE FROM ${EventDeletionSyncEntity.TABLE_NAME} " +
        "WHERE ${EventDeletionSyncEntity.PRIMARY_KEY} = :eventId")
    suspend fun deleteEventDeletionSyncEntity(eventId: String)
}
