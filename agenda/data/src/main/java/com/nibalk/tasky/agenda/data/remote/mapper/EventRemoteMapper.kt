package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.EventDto
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventPhoto
import com.nibalk.tasky.core.domain.util.toLocalDateTime
import com.nibalk.tasky.core.domain.util.toLongDateTime

fun AgendaItem.Event.toEventDto(): EventDto {
    return EventDto(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLongDateTime(),
        remindAt = remindAt.toLongDateTime(),
        endAt = endAt.toLongDateTime(),
        isHost = isHost,
        hostId = hostId,
        attendees = attendees.map { attendee ->
            attendee.toEventAttendeeDto()
        },
        photos = photos.filterIsInstance<EventPhoto.Remote>().map { remotePhoto ->
            remotePhoto.toEventPhotoDto()
        },
    )
}

fun EventDto.toAgendaItemEvent(): AgendaItem.Event {
    return AgendaItem.Event(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLocalDateTime(),
        remindAt = remindAt.toLocalDateTime(),
        endAt = endAt.toLocalDateTime(),
        hostId = hostId,
        isHost = isHost,
        attendees = attendees.map { attendeeDto ->
            attendeeDto.toEventAttendee()
        },
        photos = photos.map { photoDto ->
            photoDto.toEventPhotoRemote()
        }
    )
}
