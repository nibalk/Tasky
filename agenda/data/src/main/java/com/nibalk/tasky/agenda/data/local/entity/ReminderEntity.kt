package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = ReminderEntity.TABLE_NAME)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val id: String = UUID.randomUUID().toString(),

    val title: String,
    val description: String,
    val startAt: Long,
    val remindAt: Long,
) {
    companion object {
        const val TABLE_NAME = "reminder"
        const val PRIMARY_KEY = "id"
    }
}
