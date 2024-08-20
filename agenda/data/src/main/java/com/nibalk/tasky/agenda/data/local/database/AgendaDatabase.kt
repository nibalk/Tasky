package com.nibalk.tasky.agenda.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nibalk.tasky.agenda.data.local.dao.AgendaDao
import com.nibalk.tasky.agenda.data.local.dao.EventDao
import com.nibalk.tasky.agenda.data.local.dao.EventSyncDao
import com.nibalk.tasky.agenda.data.local.dao.ReminderDao
import com.nibalk.tasky.agenda.data.local.dao.ReminderSyncDao
import com.nibalk.tasky.agenda.data.local.dao.TaskDao
import com.nibalk.tasky.agenda.data.local.dao.TaskSyncDao
import com.nibalk.tasky.agenda.data.local.entity.AttendeeEntity
import com.nibalk.tasky.agenda.data.local.entity.EventAttendeeEntity
import com.nibalk.tasky.agenda.data.local.entity.EventDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.EventEntity
import com.nibalk.tasky.agenda.data.local.entity.EventPendingSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.ReminderDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.ReminderEntity
import com.nibalk.tasky.agenda.data.local.entity.ReminderPendingSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.TaskDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.TaskEntity
import com.nibalk.tasky.agenda.data.local.entity.TaskPendingSyncEntity


@Database(
    entities = [
        AttendeeEntity::class,
        EventAttendeeEntity::class,
        EventEntity::class,
        EventDeletionSyncEntity::class,
        EventPendingSyncEntity::class,
        TaskEntity::class,
        TaskDeletionSyncEntity::class,
        TaskPendingSyncEntity::class,
        ReminderEntity::class,
        ReminderDeletionSyncEntity::class,
        ReminderPendingSyncEntity::class,
    ],
    version = 1,
)
abstract class AgendaDatabase: RoomDatabase() {
    abstract val agendaDao: AgendaDao
    abstract val eventDao: EventDao
    abstract val eventSyncDao: EventSyncDao
    abstract val taskDao: TaskDao
    abstract val taskSyncDao: TaskSyncDao
    abstract val reminderDao: ReminderDao
    abstract val reminderSyncDao: ReminderSyncDao
}
