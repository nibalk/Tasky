package com.nibalk.tasky.agenda.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nibalk.tasky.agenda.data.local.dao.AgendaDao
import com.nibalk.tasky.agenda.data.local.dao.EventDao
import com.nibalk.tasky.agenda.data.local.dao.ReminderDao
import com.nibalk.tasky.agenda.data.local.dao.TaskDao
import com.nibalk.tasky.agenda.data.local.entity.AttendeeEntity
import com.nibalk.tasky.agenda.data.local.entity.EventAttendeeEntity
import com.nibalk.tasky.agenda.data.local.entity.EventEntity
import com.nibalk.tasky.agenda.data.local.entity.ReminderEntity
import com.nibalk.tasky.agenda.data.local.entity.TaskEntity


@Database(
    entities = [
        AttendeeEntity::class,
        EventAttendeeEntity::class,
        EventEntity::class,
        TaskEntity::class,
        ReminderEntity::class,
    ],
    version = 1,
)
abstract class AgendaDatabase: RoomDatabase() {
    abstract val agendaDao: AgendaDao
    abstract val eventDao: EventDao
    abstract val taskDao: TaskDao
    abstract val reminderDao: ReminderDao
}
