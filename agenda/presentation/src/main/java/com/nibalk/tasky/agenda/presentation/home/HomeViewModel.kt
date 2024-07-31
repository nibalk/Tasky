package com.nibalk.tasky.agenda.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.domain.usecase.DeleteEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.DeleteReminderUseCase
import com.nibalk.tasky.agenda.domain.usecase.DeleteTaskUseCase
import com.nibalk.tasky.agenda.domain.usecase.FetchAgendasByDateUseCase
import com.nibalk.tasky.agenda.domain.usecase.FetchAllAgendasUseCase
import com.nibalk.tasky.agenda.domain.usecase.FormatProfileNameUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetAgendasUseCase
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.core.domain.auth.SessionStorage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val sessionStorage: SessionStorage,
    private val formatProfileNameUseCase: FormatProfileNameUseCase,
    private val getAgendasUseCase: GetAgendasUseCase,
    private val fetchAllAgendasUseCase: FetchAllAgendasUseCase,
    private val fetchAgendasByDateUseCase: FetchAgendasByDateUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val eventChannel = Channel<HomeEvent>()
    val uiEvent: Flow<HomeEvent> = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            setProfileInitials()
            getAgendaItemsFromLocal()
        }
        viewModelScope.launch {
            getAgendaItemsFromRemote(true)
        }
    }

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnLogoutClicked -> {
                logout()
            }
            is HomeAction.OnAgendaListRefreshed -> {
                viewModelScope.launch {
                    getAgendaItemsFromRemote(false)
                }
            }
            is HomeAction.OnDayClicked -> {
                state = state.copy(
                    agendaItems = emptyList(),
                    selectedDate = action.date,
                )
                viewModelScope.launch {
                    getAgendaItemsFromLocal()
                }
            }
            is HomeAction.OnNeedleShown -> {
                state = state.copy(
                    needlePosition = action.needlePosition
                )
            }
            is HomeAction.OnListItemOptionDeleteClicked -> {
                viewModelScope.launch {
                    val agendaItemId = action.agendaItem.id
                    if (agendaItemId != null) {
                        when(action.agendaType) {
                            AgendaType.EVENT -> deleteEventUseCase.invoke(agendaItemId)
                            AgendaType.TASK -> deleteTaskUseCase.invoke(agendaItemId)
                            AgendaType.REMINDER -> deleteReminderUseCase.invoke(agendaItemId)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch {
            // logout logic
        }
    }

    private suspend fun setProfileInitials() {
        state = state.copy(
            profileInitials = formatProfileNameUseCase.invoke(
                sessionStorage.get()?.fullName.orEmpty()
            )
        )
    }

    private suspend fun getAgendaItemsFromLocal() {
        getAgendasUseCase(
            state.selectedDate
        ).collect { agendas ->
            state = state.copy(
                agendaItems = agendas.sortedBy { it.startAt },
                isLoading = false
            )
            eventChannel.send(HomeEvent.FetchAgendaSuccess)
        }
        state = state.copy(isLoading = false)
    }

    private suspend fun getAgendaItemsFromRemote(isFetchAll: Boolean) {
        if (isFetchAll) {
            fetchAllAgendasUseCase()
        } else {
            fetchAgendasByDateUseCase(state.selectedDate)
        }
    }
}
