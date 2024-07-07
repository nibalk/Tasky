package com.nibalk.tasky.agenda.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.core.domain.auth.SessionStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionStorage: SessionStorage,
    private val formatProfileNameUseCase: FormatProfileNameUseCase,
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val eventChannel = Channel<HomeEvent>()
    val uiEvent: Flow<HomeEvent> = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                profileInitials = formatProfileNameUseCase.invoke(
                    sessionStorage.get()?.fullName.orEmpty()
                )
            )
        }
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnLogoutClicked -> {
                logout()
            }
            is HomeAction.OnAgendaListRefresh -> {
                getAgendaItems()
            }
            is HomeAction.OnDayClicked -> {
                state = state.copy(
                    selectedDate = action.date
                )
            }
            else -> Unit
        }
    }

    private fun logout() {

    }

    private fun getAgendaItems() {

    }
}
