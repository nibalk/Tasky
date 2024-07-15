package com.nibalk.tasky.agenda.presentation.detail

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate
import java.time.LocalTime

sealed interface DetailAction {
    data object OnCloseClicked: DetailAction
    data object OnEditClicked: DetailAction
    data class OnSaveClicked(val agendaItem: AgendaItem) : DetailAction
    data class OnStartDateSelected(val date: LocalDate) : DetailAction
    data class OnEndDateSelected(val date: LocalDate) : DetailAction
    data class OnStartTimeSelected(val time: LocalTime) : DetailAction
    data class OnEndTimeSelected(val time: LocalTime) : DetailAction
    data class OnDeleteClicked(val agendaId: String) : DetailAction
    data class OnAddPhotoClicked(val agendaId: String) : DetailAction
    data class OnAddInviteesClicked(val agendaId: String) : DetailAction
}
