package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.EventAttendeeDto
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.core.domain.util.toLocalDateTime
import com.nibalk.tasky.core.domain.util.toLongDateTime

fun EventAttendee.toEventAttendeeDto(): EventAttendeeDto {
    return EventAttendeeDto(
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toLongDateTime()
    )
}

fun EventAttendeeDto.toEventAttendee(): EventAttendee {
    return EventAttendee(
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toLocalDateTime()
    )
}
