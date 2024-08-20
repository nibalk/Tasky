package com.nibalk.tasky.agenda.data

import android.content.Context
import com.nibalk.tasky.agenda.domain.EventRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.agenda.domain.model.EventPhoto
import com.nibalk.tasky.agenda.domain.source.local.LocalEventDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteEventDataSource
import com.nibalk.tasky.core.domain.auth.SessionStorage
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onError
import com.nibalk.tasky.core.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class OfflineFirstEventRepository(
    private val localDataSource: LocalEventDataSource,
    private val remoteDataSource: RemoteEventDataSource,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope,
    private val context: Context,
) : EventRepository {

    override suspend fun deleteEvent(eventId: String) {
        localDataSource.deleteEvent(eventId)
        localDataSource.deleteOfflineCreatedEvent(eventId)

        val remoteResult = applicationScope.async {
            remoteDataSource.deleteEvent(eventId)
        }.await()

        if(remoteResult is Result.Error) {
            applicationScope.launch {
//                syncRunScheduler.scheduleSync(
//                    type = SyncRunScheduler.SyncType.DeleteRun(id)
//                )
            }.join()
        }
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
        Timber.d("[OfflineFirst-ImageIssue] REMOTE | Get from UI to Create EVENT (%s)", event.photos)

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
                    Timber.d("[OfflineFirst-ImageIssue] REMOTE | upsert eventPhoto to local (%s)", remoteData.photos)
                    applicationScope.async {
                        localDataSource.upsertEvent(remoteData).asEmptyDataResult()
                    }.await()
                } else {
                    Result.Success(Unit)
                }
            }
        }
    }

    override suspend fun syncPendingEvents() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val createdEvent = async {
                localDataSource.getAllOfflineCreatedEvents(userId)
            }
            val deletedEvents = async {
                localDataSource.getAllOfflineDeletedEvents(userId)
            }

            val createJobs = createdEvent
                .await()
                .map { syncPendingEvent ->
                    launch {
                        val event = syncPendingEvent.event
                        event.photos
                            .filterIsInstance<EventPhoto.Local>()

                        syncPendingEvent.localPhotos.forEach {

                        }

                        when(remoteDataSource.createEvent(event)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteRunPendingSyncEntity(   it.runId)
                                }.join()
                            }
                        }
                    }
                }
            val deleteJobs = deletedEvents
                .await()
                .map {
                    launch {
                        when(remoteDataSource.deleteEvent(it.runId)) {
                            is Result.Error -> Unit
                            is Result.Success -> {
                                applicationScope.launch {
                                    runPendingSyncDao.deleteDeletedRunSyncEntity(it.runId)
                                }.join()
                            }
                        }
                    }
                }

            createJobs.forEach { it.join() }
            deleteJobs.forEach { it.join() }
        }
    }

    override suspend fun getAttendee(email: String): EventAttendee {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAttendee(eventId: String) {
        TODO("Not yet implemented")
    }
}
