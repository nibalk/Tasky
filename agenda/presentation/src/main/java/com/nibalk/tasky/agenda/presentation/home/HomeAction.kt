package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

sealed interface HomeAction {
    // Header
    data object OnProfileClicked: HomeAction
    data object OnLogoutClicked: HomeAction
    data class OnDayClicked(val date: LocalDate) : HomeAction
    // List
    data object OnAgendaListRefresh: HomeAction
    data class OnAgendaListItemClicked(val agendaItem: AgendaItem) : HomeAction
    data class OnListItemOptionsClick(val agendaItem: AgendaItem) : HomeAction
    data object OnAddAgendaOptionsClicked : HomeAction
    data object OnAddAgendaOptionTypeClicked : HomeAction
}
