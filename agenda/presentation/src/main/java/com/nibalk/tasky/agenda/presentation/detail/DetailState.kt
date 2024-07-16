package com.nibalk.tasky.agenda.presentation.detail

import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.agenda.presentation.model.NotificationDurationType
import java.time.LocalDate
import java.time.LocalTime

data class DetailState(
    // From Args
    val isEditingMode: Boolean = false,
    val selectedDate: LocalDate = LocalDate.now(),
    val agendaId: String = "",
    val agendaType: AgendaType = AgendaType.TASK,
    // From backend
    val title: String = "",
    val description: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val endDate: LocalDate = LocalDate.now(),
    val endTime: LocalTime = LocalTime.now(),
    val notificationDurationType: NotificationDurationType = NotificationDurationType.THIRTY_MINUTES,
)
