package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = EventPhotoEntity.TABLE_NAME,
    primaryKeys = [
        EventPhotoEntity.CROSS_REF_EVENT_ID,
        EventPhotoEntity.CROSS_REF_PHOTO_KEY
    ]
)
data class EventPhotoEntity(
    @ColumnInfo(name = CROSS_REF_EVENT_ID)
    val eventId: String,
    @ColumnInfo(name = CROSS_REF_PHOTO_KEY)
    val photoKey: String
) {
    companion object {
        const val TABLE_NAME = "event_photo"
        const val CROSS_REF_EVENT_ID = "eventId"
        const val CROSS_REF_PHOTO_KEY = "photoKey"
    }
}
