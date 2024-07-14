package com.nibalk.tasky.agenda.presentation.task

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

data class TaskState(
    val isEditingMode: Boolean = false,
    val agendaId: String = "",
    val agendaItem: AgendaItem.Task? = null,
    val selectedDate: LocalDate = LocalDate.now(),
)
