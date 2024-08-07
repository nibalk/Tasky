package com.nibalk.tasky.agenda.data.local.source

import android.database.sqlite.SQLiteFullException
import com.nibalk.tasky.agenda.data.local.dao.EventDao
import com.nibalk.tasky.agenda.data.local.mapper.toAgendaItemEvent
import com.nibalk.tasky.agenda.data.local.mapper.toEventEntity
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.EventId
import com.nibalk.tasky.agenda.domain.source.local.LocalEventDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.toEndOfDayMillis
import com.nibalk.tasky.core.domain.util.toStartOfDayMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class RoomLocalEventDataSource(
    private val eventDao: EventDao
) : LocalEventDataSource {

    override suspend fun getAllEvents(): Flow<List<AgendaItem.Event>> {
        return eventDao.getAllEvents().map { entities ->
            entities.map { entity ->
                entity.toAgendaItemEvent()
            }
        }
    }

    override suspend fun getEventsByDate(selectedDate: LocalDate): Flow<List<AgendaItem.Event>> {
        return eventDao.getAllEventsByDate(
            selectedDate.toStartOfDayMillis(), selectedDate.toEndOfDayMillis()
        ).map { entities ->
            entities.map { entity ->
                entity.toAgendaItemEvent()
            }
        }
    }

    override suspend fun getEventById(eventId: String): AgendaItem.Event? {
        return eventDao.getEventById(eventId)?.toAgendaItemEvent()

    }

    override suspend fun upsertEvent(event: AgendaItem.Event): Result<EventId, DataError.Local> {
        return try {
            val entity = event.toEventEntity()
            eventDao.upsertEvent(entity)
            Result.Success(entity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertEvents(events: List<AgendaItem.Event>): Result<List<EventId>, DataError.Local> {
        return try {
            val entities = events.map { item ->
                item.toEventEntity()
            }
            eventDao.updateEvents(entities)
            Result.Success(
                entities.map { entity ->
                    entity.id
                }
            )
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteEvent(eventId: String) {
        eventDao.deleteEventById(eventId)
    }

    override suspend fun deleteAllEvents() {
        eventDao.deleteAllEvents()
    }
}
