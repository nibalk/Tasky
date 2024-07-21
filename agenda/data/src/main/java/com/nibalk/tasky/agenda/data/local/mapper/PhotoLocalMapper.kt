package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.PhotoEntity
import com.nibalk.tasky.agenda.domain.model.EventPhoto

fun EventPhoto.Local.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        key = key,
        url = localUri,
        isLocal = true
    )
}

fun PhotoEntity.toEventPhotoLocal(): EventPhoto.Local {
    return EventPhoto.Local(
        key = key,
        localUri = url
    )
}

fun EventPhoto.Remote.toPhotoEntity(): PhotoEntity {
    return PhotoEntity(
        key = key,
        url = remoteUrl,
        isLocal = false
    )
}

fun PhotoEntity.toEventPhotoRemote(): EventPhoto.Remote {
    return EventPhoto.Remote(
        key = key,
        remoteUrl = url
    )
}
