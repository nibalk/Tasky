package com.nibalk.tasky.agenda.data.remote.source

import com.nibalk.tasky.agenda.data.remote.api.ReminderApi
import com.nibalk.tasky.agenda.data.remote.mapper.toAgendaItemReminder
import com.nibalk.tasky.agenda.data.remote.mapper.toReminderDto
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.source.remote.RemoteReminderDataSource
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.map
import timber.log.Timber

class RetrofitRemoteReminderDataSource(
    private val reminderApi: ReminderApi
) : RemoteReminderDataSource {
    override suspend fun getReminder(
        reminderId: String
    ): Result<AgendaItem.Reminder?, DataError.Network> {
        val response = safeCall {
            reminderApi.getReminder(reminderId)
        }
        return response.map { reminderDto ->
            reminderDto?.toAgendaItemReminder()
        }
    }

    override suspend fun createReminder(
        reminder: AgendaItem.Reminder
    ): Result<Unit, DataError.Network> {
        val response = safeCall {
            reminderApi.createReminder(reminder.toReminderDto())
        }
        Timber.d("[OfflineFirst-SaveItem] REMOTE | creating reminder(%s)", reminder.id)
        return response.asEmptyDataResult()
    }

    override suspend fun updateReminder(
        reminder: AgendaItem.Reminder
    ): Result<Unit, DataError.Network> {
        val response = safeCall {
            reminderApi.updateReminder(reminder.toReminderDto())
        }
        return response.asEmptyDataResult()
    }

    override suspend fun deleteReminder(
        reminderId: String
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            reminderApi.deleteReminder(reminderId)
        }
        return response.asEmptyDataResult()
    }
}
