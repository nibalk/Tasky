package com.nibalk.tasky.agenda.data.local.source

import android.database.sqlite.SQLiteFullException
import com.nibalk.tasky.agenda.data.local.dao.ReminderDao
import com.nibalk.tasky.agenda.data.local.mapper.toAgendaItemReminder
import com.nibalk.tasky.agenda.data.local.mapper.toReminderEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.LocalReminderDataSource
import com.nibalk.tasky.agenda.domain.source.local.ReminderId
import com.nibalk.tasky.core.data.utils.toEndOfDayMillis
import com.nibalk.tasky.core.data.utils.toStartOfDayMillis
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.LocalDate

class RoomLocalReminderDataSource(
    private val reminderDao: ReminderDao
) : LocalReminderDataSource {

    override suspend fun getAllReminders(): Flow<List<AgendaItem.Reminder>> {
        return reminderDao.getAllReminders().map { entities ->
            entities.map { entity ->
                Timber.d("[OfflineFirst-GetAll] LOCAL | reminder entity = %s", entity)
                entity.toAgendaItemReminder()
            }
        }
    }

    override suspend fun getRemindersByDate(selectedDate: LocalDate): Flow<List<AgendaItem.Reminder>> {
        Timber.d("[OfflineFirst-GetAll] LOCAL | selectedDate = %s", selectedDate)
        Timber.d("[OfflineFirst-GetAll] LOCAL | toStartOfDayMillis = %s", selectedDate.toStartOfDayMillis())
        Timber.d("[OfflineFirst-GetAll] LOCAL | toEndOfDayMillis = %s", selectedDate.toEndOfDayMillis())

        val result = reminderDao.getAllRemindersByDate(
            selectedDate.toStartOfDayMillis(), selectedDate.toEndOfDayMillis()
        ).map { entities ->
            Timber.d("[OfflineFirst-GetAll] LOCAL | entities = %s", entities[0])
            entities.map { entity ->
                Timber.d("[OfflineFirst-GetAll] LOCAL | entity = %s", entity)
                entity.toAgendaItemReminder()
            }
        }
        Timber.d("[OfflineFirst-GetAll] LOCAL | result = %s", result.toString())
        return result
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
