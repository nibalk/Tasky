package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = ReminderDeletionSyncEntity.TABLE_NAME)
data class ReminderDeletionSyncEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val reminderId: String,
    @ColumnInfo(name = USER_ID_KEY)
    val userId: String
) {
    companion object {
        const val TABLE_NAME = "reminder_deletion_sync"
        const val PRIMARY_KEY = "reminderId"
        const val USER_ID_KEY = "userId"
    }
}
