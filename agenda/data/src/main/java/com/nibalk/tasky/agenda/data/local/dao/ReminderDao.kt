package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nibalk.tasky.agenda.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Upsert
    suspend fun upsertReminder(reminder: ReminderEntity)

    @Upsert
    suspend fun updateReminders(reminders: List<ReminderEntity>)

    @Query("SELECT * FROM ${ReminderEntity.TABLE_NAME} WHERE ${ReminderEntity.PRIMARY_KEY} = :id")
    suspend fun getReminderById(id: String): ReminderEntity?

    @Query("SELECT * FROM ${ReminderEntity.TABLE_NAME} WHERE ${ReminderEntity.START_DATE_KEY} = :date")
    fun getAllRemindersByDate(date: Long): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM ${ReminderEntity.TABLE_NAME}")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Delete
    suspend fun deleteReminder(reminder: ReminderEntity)

    @Query("DELETE FROM ${ReminderEntity.TABLE_NAME} WHERE ${ReminderEntity.PRIMARY_KEY} = :id")
    suspend fun deleteReminderById(id: String)

    @Query("DELETE FROM ${ReminderEntity.TABLE_NAME}")
    suspend fun deleteAllReminders()
}
