package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

data class HomeState(
    val profileInitials: String = "",
    val agendaItems: List<AgendaItem> = emptyList(),
    val selectedAgendaItem: AgendaItem? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val currentDate: LocalDate = LocalDate.now(),
)
