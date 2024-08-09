package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventDeletionSyncEntity.TABLE_NAME)
data class EventDeletionSyncEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val eventId: String,
    @ColumnInfo(name = USER_ID_KEY)
    val userId: String
) {
    companion object {
        const val TABLE_NAME = "event_deletion_sync"
        const val PRIMARY_KEY = "eventId"
        const val USER_ID_KEY = "userId"
    }
}
