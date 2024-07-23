package com.nibalk.tasky.agenda.data.remote.mapper

import com.nibalk.tasky.agenda.data.remote.dto.EventAttendeeDto
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.core.data.utils.toLocalDateTime
import com.nibalk.tasky.core.data.utils.toLongDate

fun EventAttendee.toEventAttendeeDto(): EventAttendeeDto {
    return EventAttendeeDto(
        userId = userId,
        email = email,
        fullName = fullName,
        isGoing = isGoing,
        remindAt = remindAt.toLongDate()
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
