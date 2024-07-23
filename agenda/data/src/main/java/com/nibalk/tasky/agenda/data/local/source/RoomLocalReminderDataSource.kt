package com.nibalk.tasky.agenda.data.local.source

import android.database.sqlite.SQLiteFullException
import com.nibalk.tasky.agenda.data.local.dao.ReminderDao
import com.nibalk.tasky.agenda.data.local.mapper.toAgendaItemReminder
import com.nibalk.tasky.agenda.data.local.mapper.toReminderEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.LocalReminderDataSource
import com.nibalk.tasky.agenda.domain.source.local.ReminderId
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalReminderDataSource(
    private val reminderDao: ReminderDao
) : LocalReminderDataSource {

    override suspend fun getAllReminders(): Flow<List<AgendaItem.Reminder>> {
        return reminderDao.getAllReminders().map { entities ->
            entities.map { entity ->
                entity.toAgendaItemReminder()
            }
        }
    }

    override suspend fun getRemindersByDate(selectedDate: Long): Flow<List<AgendaItem.Reminder>> {
        return reminderDao.getAllRemindersByDate(selectedDate).map { entities ->
            entities.map { entity ->
                entity.toAgendaItemReminder()
            }
        }
    }

    override suspend fun getReminderById(reminderId: String): AgendaItem.Reminder? {
        return reminderDao.getReminderById(reminderId)?.toAgendaItemReminder()
    }

    override suspend fun upsertReminder(reminder: AgendaItem.Reminder): Result<ReminderId, DataError.Local> {
        return try {
            val entity = reminder.toReminderEntity()
            reminderDao.upsertReminder(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertReminders(reminders: List<AgendaItem.Reminder>): Result<List<ReminderId>, DataError.Local> {
        return try {
            val entities = reminders.map { item ->
                item.toReminderEntity()
            }
            reminderDao.updateReminders(entities)
            Result.Success(
                entities.map { entity ->
                    entity.id
                }
            )
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteReminder(reminderId: String) {
        reminderDao.deleteReminderById(reminderId)
    }

    override suspend fun deleteAllReminders() {
        reminderDao.deleteAllReminders()
    }
}
