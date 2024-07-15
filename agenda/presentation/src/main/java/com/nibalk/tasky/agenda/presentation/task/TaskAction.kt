package com.nibalk.tasky.agenda.presentation.task

import com.nibalk.tasky.agenda.domain.model.AgendaItem

sealed interface TaskAction {
    data object OnCloseClicked: TaskAction
    data object OnEditClicked: TaskAction
    data class OnSaveClicked(val agendaItem: AgendaItem) : TaskAction
    data class OnDeleteClicked(val agendaId: String) : TaskAction
}
