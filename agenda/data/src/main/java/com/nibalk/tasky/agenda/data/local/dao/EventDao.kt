package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nibalk.tasky.agenda.data.local.entity.EventEntity
import com.nibalk.tasky.agenda.data.local.entity.EventEntityFull
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Upsert
    suspend fun upsertEvent(event: EventEntity)

    @Upsert
    suspend fun updateEvents(events: List<EventEntity>)

    @Transaction
    @Query("SELECT * FROM ${EventEntity.TABLE_NAME} WHERE ${EventEntity.PRIMARY_KEY} = :id")
    suspend fun getEventById(id: String): EventEntityFull?

    @Transaction
    @Query("SELECT * FROM ${EventEntity.TABLE_NAME}")
    fun getAllEvents(): Flow<List<EventEntityFull>>

    @Delete
    suspend fun deleteEvent(event: EventEntity)

    @Query("DELETE FROM ${EventEntity.TABLE_NAME}")
    suspend fun deleteAllEvents()
}
