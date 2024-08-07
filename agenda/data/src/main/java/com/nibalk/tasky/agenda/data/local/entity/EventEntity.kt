package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.nibalk.tasky.agenda.data.local.converter.EventPhotoListConverter
import com.nibalk.tasky.agenda.data.remote.dto.EventPhotoDto

@Entity(tableName = EventEntity.TABLE_NAME)
@TypeConverters(EventPhotoListConverter::class)
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val id: String,

    val title: String,
    val description: String,
    val startAt: Long,
    val remindAt: Long,
    val endAt: Long,
    val isHost: Boolean,
    val hostId: String,
    val remotePhotos: List<EventPhotoDto>,
) {
    companion object {
        const val TABLE_NAME = "event"
        const val PRIMARY_KEY = "eventId"
        const val START_DATE_KEY = "startAt"
    }
}

data class EventEntityFull(
    @Embedded
    val event: EventEntity,
    @Relation(
        parentColumn = EventEntity.PRIMARY_KEY,
        entityColumn = AttendeeEntity.PRIMARY_KEY,
        associateBy = Junction(EventAttendeeEntity::class)
    )
    val attendees: List<AttendeeEntity>,
)

