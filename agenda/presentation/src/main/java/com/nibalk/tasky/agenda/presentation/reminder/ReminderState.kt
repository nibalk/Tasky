package com.nibalk.tasky.agenda.presentation.reminder

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

data class ReminderState(
    val isEditingMode: Boolean = false,
    val agendaId: String = "",
    val agendaItem: AgendaItem.Reminder? = null,
    val selectedDate: LocalDate = LocalDate.now(),
)
