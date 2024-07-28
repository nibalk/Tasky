package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.ReminderRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

class SaveReminderUseCase(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminder: AgendaItem.Reminder): EmptyResult<DataError> {
        return if (reminder.id.isNullOrEmpty()) {
            reminderRepository.createReminder(reminder)
        } else {
            reminderRepository.updateReminder(reminder)
        }
    }
}
