package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReminderDto(
    val id: String?,
    val title: String,
    val description: String,
    @SerialName("time") val startAt: Long,
    val remindAt: Long,
)
