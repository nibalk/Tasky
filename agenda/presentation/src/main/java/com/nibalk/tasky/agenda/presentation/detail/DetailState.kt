package com.nibalk.tasky.agenda.presentation.detail

import com.nibalk.tasky.agenda.domain.model.EventAttendee
import com.nibalk.tasky.agenda.domain.model.EventPhoto
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
    // The rest
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val title: String = "",
    val description: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val startTime: LocalTime = LocalTime.now(),
    val remindDate: LocalDate = LocalDate.now(),
    val remindTime: LocalTime = LocalTime.now(),
    val details: AgendaItemDetails,
    val notificationDurationType: NotificationDurationType = NotificationDurationType.THIRTY_MINUTES,
)

sealed interface AgendaItemDetails {
    data class Event(
        val endDate: LocalDate = LocalDate.now(),
        val endTime: LocalTime = LocalTime.now(),
        val isHost: Boolean = false,
        val hostId: String = "",
        val attendees: List<EventAttendee> = emptyList(),
        val photos: List<EventPhoto> = emptyList(),
    ): AgendaItemDetails

    data class Task(
        val isDone: Boolean = false
    ): AgendaItemDetails

    data object Reminder: AgendaItemDetails
}

val AgendaItemDetails.asEventDetails: AgendaItemDetails.Event
    get() = this as AgendaItemDetails.Event

val AgendaItemDetails.asTaskDetails: AgendaItemDetails.Task
    get() = this as AgendaItemDetails.Task
