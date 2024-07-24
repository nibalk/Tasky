package com.nibalk.tasky.agenda.domain.source.remote

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result

interface RemoteReminderDataSource {
    suspend fun getReminder(
        reminderId: String
    ): Result<AgendaItem.Reminder?, DataError.Network>

    suspend fun createReminder(
        reminder: AgendaItem.Reminder
    ): EmptyResult<DataError.Network>

    suspend fun updateReminder(
        reminder: AgendaItem.Reminder
    ): EmptyResult<DataError.Network>

    suspend fun deleteReminder(
        reminderId: String
    ): EmptyResult<DataError.Network>
}
