package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.EventDeletionSyncEntity
import com.nibalk.tasky.agenda.data.local.entity.EventEntity
import com.nibalk.tasky.agenda.data.local.entity.EventEntityFull
import com.nibalk.tasky.agenda.data.local.entity.EventPendingSyncEntity
import com.nibalk.tasky.agenda.data.remote.mapper.toEventPhotoDto
import com.nibalk.tasky.agenda.data.remote.mapper.toEventPhotoRemote
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.agenda.domain.model.EventPhoto
import com.nibalk.tasky.agenda.domain.model.SyncDeletionAgendaItem
import com.nibalk.tasky.agenda.domain.model.SyncPendingEvent
import com.nibalk.tasky.core.domain.util.toEpochMillis
import com.nibalk.tasky.core.domain.util.toLocalDateTime

// Mapping EventEntity - with Attendees and Photos

fun EventEntityFull.toAgendaItemEvent(): AgendaItem.Event {
    val attendees = attendees.map { agendaEntity ->
        agendaEntity.toEventAttendee()
    }
    return event.toAgendaItemEvent(attendees)
}

fun AgendaItem.Event.toEventEntityFull(): EventEntityFull {
    return EventEntityFull(
        event = toEventEntity(),
        attendees = attendees.map { attendee ->
            attendee.toAttendeeEntity()
        },
    )
}

// Mapping EventEntity

fun AgendaItem.Event.toEventEntity(): EventEntity {
    return EventEntity(
        id = id.orEmpty(),
        title = title,
        description = description,
        startAt = startAt.toEpochMillis(),
        remindAt = remindAt.toEpochMillis(),
        endAt = endAt.toEpochMillis(),
        isHost = isHost,
        hostId = hostId,
        remotePhotos = photos.filterIsInstance<EventPhoto.Remote>().map { remotePhoto ->
            remotePhoto.toEventPhotoDto()
        },
    )
}

fun EventEntity.toAgendaItemEvent(
    attendees: List<EventAttendee>,
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
        photos = remotePhotos.map { photoDto ->
            photoDto.toEventPhotoRemote()
        }
    )
}

// Mapping EventPendingSyncEntity - with Attendees and Photos

fun EventPendingSyncEntity.toSyncPendingEvent(): SyncPendingEvent {
    val attendees: List<EventAttendee> = emptyList() //TODO
    return SyncPendingEvent(
        userId = userId,
        event = event.toAgendaItemEvent(attendees),
        localPhotos = localPhotos
    )
}

// Mapping EventDeletionSyncEntity

fun EventDeletionSyncEntity.toSyncDeletionAgendaItem(): SyncDeletionAgendaItem {
    return SyncDeletionAgendaItem(
        userId = userId,
        agendaId = eventId
    )
}
