package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import java.time.LocalDate

sealed interface HomeAction {
    // Home - Profile and Logout
    data object OnProfileClicked: HomeAction
    data object OnLogoutClicked: HomeAction
    // Home - Day and Month Picker
    data class OnDayClicked(val date: LocalDate) : HomeAction
    data class OnMonthClicked(val month: Int) : HomeAction
    // Agenda - List
    data object OnAgendaListRefresh: HomeAction
    data class OnAgendaListItemClicked(val agendaItem: AgendaItem) : HomeAction
    data class OnListItemOptionsClick(val agendaItem: AgendaItem) : HomeAction
    // Agenda - Add
    data object OnAddAgendaOptionsClicked : HomeAction
    data object OnAddAgendaOptionTypeClicked : HomeAction
}
