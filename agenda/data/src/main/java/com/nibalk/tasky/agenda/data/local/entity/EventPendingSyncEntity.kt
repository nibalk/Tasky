package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EventPendingSyncEntity.TABLE_NAME)
data class EventPendingSyncEntity(
    @Embedded val event: EventEntity,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val eventId: String = event.id,
    val localPhotos: List<ByteArray>,
    @ColumnInfo(name = USER_ID_KEY) val userId: String,
) {
    companion object {
        const val TABLE_NAME = "event_pending_sync"
        const val PRIMARY_KEY = "eventId"
        const val USER_ID_KEY = "userId"
    }
}
