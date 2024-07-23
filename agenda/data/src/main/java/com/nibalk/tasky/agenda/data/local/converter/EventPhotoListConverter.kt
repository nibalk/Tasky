package com.nibalk.tasky.agenda.data.local.converter

import androidx.room.TypeConverter
import com.nibalk.tasky.agenda.data.remote.dto.EventPhotoDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class EventPhotoListConverter {
    @TypeConverter
    fun fromString(value: String?): List<EventPhotoDto>? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun toString(list: List<EventPhotoDto>?): String? {
        return list?.let { Json.encodeToString(it) }
    }
}
