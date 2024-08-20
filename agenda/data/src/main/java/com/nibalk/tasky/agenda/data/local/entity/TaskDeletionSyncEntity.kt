package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TaskDeletionSyncEntity.TABLE_NAME)
data class TaskDeletionSyncEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val taskId: String,
    @ColumnInfo(name = USER_ID_KEY)
    val userId: String
) {
    companion object {
        const val TABLE_NAME = "task_deletion_sync"
        const val PRIMARY_KEY = "taskId"
        const val USER_ID_KEY = "userId"
    }
}
