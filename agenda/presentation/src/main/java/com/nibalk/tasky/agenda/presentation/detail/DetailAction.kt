package com.nibalk.tasky.agenda.presentation.detail

import com.nibalk.tasky.agenda.presentation.model.NotificationDurationType
import java.time.LocalDate
import java.time.LocalTime

sealed interface DetailAction {
    data object OnCloseClicked : DetailAction
    data object OnSaveClicked : DetailAction
    data class OnIsEditableChanged(val isEditable: Boolean) : DetailAction
    data object OnTitleClicked : DetailAction
    data object OnDescriptionClicked : DetailAction
    data class OnNotificationDurationClicked(val type: NotificationDurationType) : DetailAction
    data class OnStartDateSelected(val date: LocalDate) : DetailAction
    data class OnEndDateSelected(val date: LocalDate) : DetailAction
    data class OnStartTimeSelected(val time: LocalTime) : DetailAction
    data class OnEndTimeSelected(val time: LocalTime) : DetailAction
    data class OnDeleteClicked(val agendaId: String) : DetailAction
    data class OnAddPhotoClicked(val agendaId: String) : DetailAction
    data class OnAddInviteesClicked(val agendaId: String) : DetailAction
}
