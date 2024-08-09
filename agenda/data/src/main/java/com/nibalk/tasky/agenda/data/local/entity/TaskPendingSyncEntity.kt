package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TaskPendingSyncEntity.TABLE_NAME)
data class TaskPendingSyncEntity(
    @Embedded val task: TaskEntity,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val taskId: String = task.id,
    @ColumnInfo(name = USER_ID_KEY)
    val userId: String
) {
    companion object {
        const val TABLE_NAME = "task_pending_sync"
        const val PRIMARY_KEY = "taskId"
        const val USER_ID_KEY = "userId"
    }
}
