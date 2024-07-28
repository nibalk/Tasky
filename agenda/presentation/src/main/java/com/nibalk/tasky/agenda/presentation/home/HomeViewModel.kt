package com.nibalk.tasky.agenda.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.core.domain.auth.SessionStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionStorage: SessionStorage,
    private val formatProfileNameUseCase: FormatProfileNameUseCase,
    private val getAgendasUseCase: GetAgendasUseCase,
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val eventChannel = Channel<HomeEvent>()
    val uiEvent: Flow<HomeEvent> = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            setProfileInitials()
            getAgendaItems()
        }
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnLogoutClicked -> {
                logout()
            }
            is HomeAction.OnAgendaListRefreshed -> {
                //viewModelScope.launch { fetchAgendaItems() }
            }
            is HomeAction.OnDayClicked -> {
                state = state.copy(
                    agendaItems = emptyList(),
                    selectedDate = action.date,
                )
            }
            is HomeAction.OnNeedleShown -> {
                state = state.copy(
                    needlePosition = action.needlePosition
                )
            }
            else -> Unit
        }
    }

    private fun logout() {

    }

    private suspend fun setProfileInitials() {
        state = state.copy(
            profileInitials = formatProfileNameUseCase.invoke(
                sessionStorage.get()?.fullName.orEmpty()
            )
        )
    }

    private suspend fun getAgendaItems() {
        state = state.copy(isLoading = true)
        getAgendasUseCase(
            state.selectedDate
        ).onEach { agendas ->
            state = state.copy(
                agendaItems = agendas.sortedBy { it.startAt },
                isLoading = false
            )
            eventChannel.send(HomeEvent.FetchAgendaSuccess)
        }
        state = state.copy(isLoading = false)
    }
}
