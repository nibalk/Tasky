package com.nibalk.tasky.agenda.presentation.reminder

import com.nibalk.tasky.agenda.domain.model.AgendaItem

sealed interface ReminderAction {
    data object OnCloseClicked: ReminderAction
    data object OnEditClicked: ReminderAction
    data class OnSaveClicked(val agendaItem: AgendaItem) : ReminderAction
    data class OnDeleteClicked(val agendaId: String) : ReminderAction
    data class OnAddPhotoClicked(val agendaId: String) : ReminderAction
    data class OnAddInviteesClicked(val agendaId: String) : ReminderAction
}
