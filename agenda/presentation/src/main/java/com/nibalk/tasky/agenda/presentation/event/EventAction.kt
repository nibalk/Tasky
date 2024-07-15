package com.nibalk.tasky.agenda.presentation.event

import com.nibalk.tasky.agenda.domain.model.AgendaItem

sealed interface EventAction {
    data object OnCloseClicked: EventAction
    data object OnEditClicked: EventAction
    data class OnSaveClicked(val agendaItem: AgendaItem) : EventAction
    data class OnDeleteClicked(val agendaId: String) : EventAction
    data class OnAddPhotoClicked(val agendaId: String) : EventAction
    data class OnAddInviteesClicked(val agendaId: String) : EventAction
}
