package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = EventAttendeeEntity.TABLE_NAME,
    primaryKeys = [
        EventAttendeeEntity.CROSS_REF_EVENT_ID,
        EventAttendeeEntity.CROSS_REF_ATTENDEE_ID
    ],
    indices = [
        Index(value = [EventAttendeeEntity.CROSS_REF_ATTENDEE_ID])
    ]
)
data class EventAttendeeEntity(
    @ColumnInfo(name = CROSS_REF_EVENT_ID)
    val eventId: String,
    @ColumnInfo(name = CROSS_REF_ATTENDEE_ID)
    val attendeeId: String
) {
    companion object {
        const val TABLE_NAME = "event_attendee"
        const val CROSS_REF_EVENT_ID = "eventId"
        const val CROSS_REF_ATTENDEE_ID = "attendeeId"
    }
}
