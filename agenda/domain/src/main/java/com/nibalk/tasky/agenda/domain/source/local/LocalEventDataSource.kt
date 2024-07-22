package com.nibalk.tasky.agenda.domain.source.local

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias EventId = String

interface LocalEventDataSource {
    suspend fun getAllEvents(): Flow<List<AgendaItem.Event>>
    suspend fun getEventsByDate(selectedDate: Long): Flow<List<AgendaItem.Event>>
    suspend fun getEventById(eventId: String): AgendaItem.Event?

    suspend fun upsertEvent(
        event: AgendaItem.Event
    ): Result<EventId, DataError.Local>
    suspend fun upsertEvents(
        events: List<AgendaItem.Event>
    ): Result<List<EventId>, DataError.Local>

    suspend fun deleteEvent(eventId: String)
    suspend fun deleteAllEvents()
}
