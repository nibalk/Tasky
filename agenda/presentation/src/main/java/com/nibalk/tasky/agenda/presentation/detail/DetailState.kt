package com.nibalk.tasky.agenda.presentation.detail

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import java.time.LocalDate

data class DetailState(
    val isEditingMode: Boolean = false,
    val agendaId: String = "",
    val agendaType: AgendaType? = null,
    val agendaItem: AgendaItem? = null,
    val selectedDate: LocalDate = LocalDate.now(),
)
