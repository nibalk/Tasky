package com.nibalk.tasky.agenda.domain.source.local

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

typealias ReminderId = String

interface LocalReminderDataSource {
    suspend fun getAllReminders(): Flow<List<AgendaItem.Reminder>>
    suspend fun getRemindersByDate(selectedDate: LocalDate): Flow<List<AgendaItem.Reminder>>
    suspend fun getReminderById(reminderId: String): AgendaItem.Reminder?

    suspend fun upsertReminder(
        reminder: AgendaItem.Reminder
    ): Result<ReminderId, DataError.Local>
    suspend fun upsertReminders(
        reminders: List<AgendaItem.Reminder>
    ): Result<List<ReminderId>, DataError.Local>

    suspend fun deleteReminder(reminderId: String)
    suspend fun deleteAllReminders()
}

