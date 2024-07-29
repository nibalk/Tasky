package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.ReminderRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem

class GetReminderUseCase(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String): AgendaItem.Reminder? {
        return reminderRepository.getReminder(reminderId)
    }
}
