package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.EventRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.agenda.domain.source.local.LocalEventDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteEventDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onError

class OfflineFirstEventRepository(
    private val localDataSource: LocalEventDataSource,
    private val remoteDataSource: RemoteEventDataSource,
) : EventRepository {

    override suspend fun deleteEvent(eventId: String) {
        localDataSource.deleteEvent(eventId)
    }

    override suspend fun getEvent(eventId: String): AgendaItem.Event? {
        return localDataSource.getEventById(eventId)
    }

    override suspend fun createEvent(event: AgendaItem.Event): EmptyResult<DataError> {
        val localResult = localDataSource.upsertEvent(event)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }
        val remoteResult = remoteDataSource.createEvent(event)
        return remoteResult.asEmptyDataResult()
    }

    override suspend fun updateEvent(event: AgendaItem.Event): EmptyResult<DataError> {
        val localResult = localDataSource.upsertEvent(event)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }
        val remoteResult = remoteDataSource.updateEvent(event)
        return remoteResult.asEmptyDataResult()
    }

    override suspend fun getAttendee(email: String): EventAttendee {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttendee(eventId: String) {
        TODO("Not yet implemented")
    }


}
