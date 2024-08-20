package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ReminderPendingSyncEntity.TABLE_NAME)
data class ReminderPendingSyncEntity(
    @Embedded val reminder: ReminderEntity,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val reminderId: String = reminder.id,
    @ColumnInfo(name = USER_ID_KEY)
    val userId: String
) {
    companion object {
        const val TABLE_NAME = "reminder_pending_sync"
        const val PRIMARY_KEY = "reminderId"
        const val USER_ID_KEY = "userId"
    }
}
