package com.nibalk.tasky.agenda.presentation.home

import com.nibalk.tasky.core.presentation.utils.UiText

sealed interface HomeEvent {
    data object FetchAgendaSuccess: HomeEvent
    data class FetchAgendaError(val error: UiText): HomeEvent
    data object LogoutSuccess: HomeEvent
    data class LogoutError(val error: UiText): HomeEvent
}
