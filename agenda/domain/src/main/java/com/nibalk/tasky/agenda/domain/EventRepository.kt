package com.nibalk.tasky.agenda.domain

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

interface EventRepository {
    suspend fun deleteEvent(eventId: String)
    suspend fun getEvent(eventId: String): AgendaItem.Event?
    suspend fun createEvent(event: AgendaItem.Event): EmptyResult<DataError>
    suspend fun updateEvent(event: AgendaItem.Event): EmptyResult<DataError>

    suspend fun syncPendingEvents()

    suspend fun getAttendee(email: String): EventAttendee
    suspend fun deleteAttendee(eventId: String)
}
