package com.nibalk.tasky.agenda.domain.model

sealed class EventPhoto (val location: String) {
    data class Local(
        val key: String,
        val localUri: String
    ) : EventPhoto(localUri)

    data class Remote(
        val key: String,
        val remoteUrl: String,
    ) : EventPhoto(remoteUrl)
}
