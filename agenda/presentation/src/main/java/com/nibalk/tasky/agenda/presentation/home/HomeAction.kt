package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import java.time.LocalDate

sealed interface HomeAction {
    // Header
    data object OnLogoutClicked: HomeAction
    data class OnDayClicked(val date: LocalDate) : HomeAction
    // List
    data object OnAgendaListRefresh: HomeAction
    data class OnAgendaListItemClicked(val agendaItem: AgendaItem) : HomeAction
    data class OnListItemOptionsClick(val agendaItem: AgendaItem) : HomeAction
    data class OnAddAgendaOptionsClicked(val agendaType: AgendaType) : HomeAction
    data object OnAddAgendaOptionTypeClicked : HomeAction
}
