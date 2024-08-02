package com.nibalk.tasky.agenda.data.local.mapper

import com.nibalk.tasky.agenda.data.local.entity.AttendeeEntity
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.core.domain.util.toLocalDateTime
import com.nibalk.tasky.core.domain.util.toEpochMillis

fun EventAttendee.toAttendeeEntity(): AttendeeEntity {
    return AttendeeEntity(
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toEpochMillis()
    )
}

fun AttendeeEntity.toEventAttendee(): EventAttendee {
    return EventAttendee(
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toLocalDateTime()
    )
}
