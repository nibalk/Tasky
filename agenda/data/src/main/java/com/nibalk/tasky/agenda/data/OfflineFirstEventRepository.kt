package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.EventRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.agenda.domain.source.local.LocalEventDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteEventDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onError
import com.nibalk.tasky.core.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import timber.log.Timber

class OfflineFirstEventRepository(
    private val localDataSource: LocalEventDataSource,
    private val remoteDataSource: RemoteEventDataSource,
    private val applicationScope: CoroutineScope
) : EventRepository {

    override suspend fun deleteEvent(eventId: String) {
        localDataSource.deleteEvent(eventId)

        applicationScope.async {
            remoteDataSource.deleteEvent(eventId)
        }.await()
    }

    override suspend fun getEvent(eventId: String): AgendaItem.Event? {
        return localDataSource.getEventById(eventId)
    }

    override suspend fun createEvent(event: AgendaItem.Event): EmptyResult<DataError> {
        val localResult = localDataSource.upsertEvent(event)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }.onSuccess { id ->
            Timber.d("[OfflineFirst-SaveItem] LOCAL | Created EVENT (%s)", id)
        }

        return when(val remoteResult = remoteDataSource.createEvent(event)) {
            is Result.Error -> {
                Result.Success(Unit)
            }
            is Result.Success -> {
                val remoteData = remoteResult.data
                if (remoteData != null) {
                    applicationScope.async {
                        localDataSource.upsertEvent(remoteData).asEmptyDataResult()
                    }.await()
                } else {
                    Result.Success(Unit)
                }
            }
        }
    }

    override suspend fun updateEvent(event: AgendaItem.Event): EmptyResult<DataError> {
        val localResult = localDataSource.upsertEvent(event)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }
        return when(val remoteResult = remoteDataSource.updateEvent(event)) {
            is Result.Error -> {
                Result.Success(Unit)
            }
            is Result.Success -> {
                val remoteData = remoteResult.data
                if (remoteData != null) {
                    applicationScope.async {
                        localDataSource.upsertEvent(remoteData).asEmptyDataResult()
                    }.await()
                } else {
                    Result.Success(Unit)
                }
            }
        }
    }

    override suspend fun getAttendee(email: String): EventAttendee {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttendee(eventId: String) {
        TODO("Not yet implemented")
    }
}
