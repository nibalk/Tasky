package com.nibalk.tasky.agenda.data.local.dao

import androidx.room.Dao
import androidx.room.Transaction
import com.nibalk.tasky.agenda.data.local.database.AgendaDatabase
import com.nibalk.tasky.agenda.data.local.mapper.toEventEntity
import com.nibalk.tasky.agenda.data.local.mapper.toReminderEntity
import com.nibalk.tasky.agenda.data.local.mapper.toTaskEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItems

@Dao
interface AgendaDao {
    @Transaction
    suspend fun updateAgendas(db: AgendaDatabase, agendaItems: AgendaItems) {
        db.apply {
            taskDao.deleteAllTasks()
            reminderDao.deleteAllReminders()
            eventDao.deleteAllEvents()

            agendaItems.tasks.forEach { task ->
                taskDao.upsertTask(task.toTaskEntity())
            }
            agendaItems.reminders.forEach { reminder ->
                reminderDao.upsertReminder(reminder.toReminderEntity())
            }
            agendaItems.events.forEach { event ->
                eventDao.upsertEvent(event.toEventEntity())
            }
        }
    }
}
