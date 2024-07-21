package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.EventEntity
import com.nibalk.tasky.agenda.data.local.entity.EventEntityWithRelations
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.agenda.domain.model.EventPhoto
import com.nibalk.tasky.core.data.utils.toLocalDateTime
import com.nibalk.tasky.core.data.utils.toLongDate
import java.util.UUID

// Mapping EventEntity - with Attendees and Photos

fun EventEntityWithRelations.toAgendaItemEvent(): AgendaItem.Event {
    val attendees = attendees.map { agendaEntity ->
        agendaEntity.toEventAttendee()
    }
    val photos = this.photos.map { photo ->
        if (photo.isLocal) {
            photo.toEventPhotoLocal()
        } else {
            photo.toEventPhotoRemote()
        }
    }
    return event.toAgendaItemEvent(attendees, photos)
}

fun AgendaItem.Event.toEventEntityAll(): EventEntityWithRelations {
    return EventEntityWithRelations(
        event = toEventEntity(),
        attendees = attendees.map { attendee ->
            attendee.toAttendeeEntity()
        },
        photos = photos.map { photo ->
            when (photo) {
                is EventPhoto.Local -> photo.toPhotoEntity()
                is EventPhoto.Remote -> photo.toPhotoEntity()
            }
        },
    )
}

// Mapping EventEntity

private fun AgendaItem.Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = id ?: UUID.randomUUID().toString(),
        title = title,
        description = description,
        startAt = startAt.toLongDate(),
        remindAt = remindAt.toLongDate(),
        endAt = endAt.toLongDate(),
        isHost = isHost,
        hostId = hostId,
    )
}

private fun EventEntity.toAgendaItemEvent(
    attendees: List<EventAttendee>,
    photos: List<EventPhoto>
): AgendaItem.Event {
    return AgendaItem.Event(
        id = id,
        title = title,
        description = description,
        startAt = startAt.toLocalDateTime(),
        remindAt = remindAt.toLocalDateTime(),
        endAt = endAt.toLocalDateTime(),
        hostId = hostId,
        isHost = isHost,
        attendees = attendees,
        photos = photos
    )
}
