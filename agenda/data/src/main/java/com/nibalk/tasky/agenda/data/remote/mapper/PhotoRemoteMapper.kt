package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.EventPhotoDto
import com.nibalk.tasky.agenda.domain.model.EventPhoto

fun EventPhotoDto.toEventPhotoRemote(): EventPhoto.Remote {
    return EventPhoto.Remote(
        key = key,
        remoteUrl = url
    )
}

fun EventPhoto.Remote.toEventPhotoDto(): EventPhotoDto {
    return EventPhotoDto(
        key = key,
        url = remoteUrl
    )
}
