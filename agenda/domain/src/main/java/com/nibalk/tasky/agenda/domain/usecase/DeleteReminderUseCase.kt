package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.ReminderRepository

class DeleteReminderUseCase(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String) {
        return reminderRepository.deleteReminder(reminderId)
    }
}
