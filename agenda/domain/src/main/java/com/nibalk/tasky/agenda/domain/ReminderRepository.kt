package com.nibalk.tasky.agenda.domain

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

interface ReminderRepository {
    suspend fun deleteReminder(reminderId: String)
    suspend fun getReminder(reminderId: String): AgendaItem.Reminder?
    suspend fun createReminder(reminder: AgendaItem.Reminder): EmptyResult<DataError>
    suspend fun updateReminder(reminder: AgendaItem.Reminder): EmptyResult<DataError>
}
