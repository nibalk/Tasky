package com.nibalk.tasky.agenda.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PhotoEntity.TABLE_NAME)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = PRIMARY_KEY)
    val key: String,
    val url: String,
    val isLocal: Boolean,
) {
    companion object {
        const val TABLE_NAME = "photo"
        const val PRIMARY_KEY = "photoKey"
    }
}
