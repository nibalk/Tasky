package com.nibalk.tasky.agenda.presentation.model

import java.time.LocalDate

data class AgendaArgs(
    val isEditable: Boolean,
    val selectedDate: LocalDate?,
    val agendaId: String?,
)
