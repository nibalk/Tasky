package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.ReminderRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.local.LocalReminderDataSource
import com.nibalk.tasky.agenda.domain.source.remote.RemoteReminderDataSource
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onError

class OfflineFirstReminderRepository(
    private val localDataSource: LocalReminderDataSource,
    private val remoteDataSource: RemoteReminderDataSource,
) : ReminderRepository {
    override suspend fun deleteReminder(reminderId: String) {
        localDataSource.deleteReminder(reminderId)
    }

    override suspend fun getReminder(reminderId: String): AgendaItem.Reminder? {
        return localDataSource.getReminderById(reminderId)
    }

    override suspend fun createReminder(reminder: AgendaItem.Reminder): EmptyResult<DataError> {
        val localResult = localDataSource.upsertReminder(reminder)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }
        val remoteResult = remoteDataSource.createReminder(reminder)
        return remoteResult.asEmptyDataResult()
    }

    override suspend fun updateReminder(reminder: AgendaItem.Reminder): EmptyResult<DataError> {
        val localResult = localDataSource.upsertReminder(reminder)
        localResult.onError {
            return localResult.asEmptyDataResult()
        }
        val remoteResult = remoteDataSource.updateReminder(reminder)
        return remoteResult.asEmptyDataResult()
    }

}
