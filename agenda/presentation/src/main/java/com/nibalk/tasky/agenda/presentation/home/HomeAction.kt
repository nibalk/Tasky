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
    data class OnNeedleShown(val needlePosition: Int) : HomeAction
    // List Item
    data class OnListItemOptionOpenClicked(
        val agendaItem: AgendaItem,
        val agendaType: AgendaType
    ) : HomeAction
    data class OnListItemOptionEditClicked(
        val agendaItem: AgendaItem,
        val agendaType: AgendaType
    ) : HomeAction
    data class OnListItemOptionDeleteClicked(
        val agendaItem: AgendaItem,
        val agendaType: AgendaType
    ) : HomeAction
}
