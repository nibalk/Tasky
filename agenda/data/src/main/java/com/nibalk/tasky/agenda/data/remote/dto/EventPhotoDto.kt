package com.nibalk.tasky.agenda.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventPhotoDto (
    val key: String,
    val url: String
)
