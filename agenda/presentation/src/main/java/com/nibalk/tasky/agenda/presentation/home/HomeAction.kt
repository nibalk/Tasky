package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import java.time.LocalDate

sealed interface HomeAction {
    // Header
    data object OnLogoutClicked: HomeAction
    data class OnDayClicked(val date: LocalDate) : HomeAction
    // List
    data object OnAgendaListRefreshed: HomeAction
    data class OnAddAgendaOptionsClicked(val agendaType: AgendaType) : HomeAction
    // List Item
    data class OnListItemOptionOpenClick(
        val agendaItem: AgendaItem,
        val agendaType: AgendaType
    ) : HomeAction
    data class OnListItemOptionEditClick(
        val agendaItem: AgendaItem,
        val agendaType: AgendaType
    ) : HomeAction
    data class OnListItemOptionDeleteClick(
        val agendaItem: AgendaItem,
        val agendaType: AgendaType
    ) : HomeAction
}
