package com.nibalk.tasky.agenda.presentation.event

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

data class EventState(
    val isEditingMode: Boolean = false,
    val agendaId: String = "",
    val agendaItem: AgendaItem.Event? = null,
    val selectedDate: LocalDate = LocalDate.now(),
)
