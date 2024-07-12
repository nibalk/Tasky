package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

data class HomeState(
    val profileInitials: String = "",
    val agendaItems: List<AgendaItem> = emptyList(),
    val isLoading: Boolean = false,
    val selectedAgendaItem: AgendaItem? = null,
    val selectedDate: LocalDate = LocalDate.now(),
    val currentDate: LocalDate = LocalDate.now(),
    val needlePosition: Int? = null,
)
