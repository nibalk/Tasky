package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AttendeeEntity.TABLE_NAME)
data class AttendeeEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val userId: String,

    val email: String,
    val fullName: String,
    val isGoing: Boolean,
    val remindAt: Long,
) {
    companion object {
        const val TABLE_NAME = "attendee"
        const val PRIMARY_KEY = "attendeeId"
    }
}
