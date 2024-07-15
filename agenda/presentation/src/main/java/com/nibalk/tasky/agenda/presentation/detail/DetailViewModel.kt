package com.nibalk.tasky.agenda.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.test.mock.AgendaSampleData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class DetailViewModel(
    private val agendaArgs: AgendaArgs
): ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    private val eventChannel = Channel<DetailState>()
    val uiEvent: Flow<DetailState> = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                isEditingMode = agendaArgs.isEditable,
                selectedDate = agendaArgs.selectedDate ?: LocalDate.now(),
                agendaType = AgendaType.valueOf(agendaArgs.agendaType),
                agendaId = agendaArgs.agendaId.orEmpty(),

                )
        }
        viewModelScope.launch {
            fetchAgendaItem()
        }
    }

    fun onAction(action: DetailAction) {
        when(action) {
            is DetailAction.OnStartDateSelected -> {
                state = state.copy(startDate = action.date)
            }
            is DetailAction.OnEndDateSelected -> {
                state = state.copy(endDate = action.date)
            }
            is DetailAction.OnStartTimeSelected -> {
                state = state.copy(startTime = action.time)
            }
            is DetailAction.OnEndTimeSelected -> {
                state = state.copy(endTime = action.time)
            }
            else -> Unit
        }
    }

    private suspend fun fetchAgendaItem() {
        val item = when(AgendaType.valueOf(agendaArgs.agendaType)) { //TODO: Mocking for now
            AgendaType.EVENT -> {
                AgendaSampleData.allAgendas.find {
                    it is AgendaItem.Event && it.id == agendaArgs.agendaId
                } as? AgendaItem.Event
            }
            AgendaType.TASK -> {
                AgendaSampleData.allAgendas.find {
                    it is AgendaItem.Task && it.id == agendaArgs.agendaId
                } as? AgendaItem.Task
            }
            AgendaType.REMINDER -> {
                AgendaSampleData.allAgendas.find {
                    it is AgendaItem.Reminder && it.id == agendaArgs.agendaId
                } as? AgendaItem.Reminder
            }
        }
        state = state.copy(
            title = item?.title.orEmpty(),
            description = item?.title.orEmpty(),
            startDate = item?.startAt?.toLocalDate() ?: state.selectedDate,
            startTime = item?.startAt?.toLocalTime() ?: LocalTime.now(),
            endDate = if (item is AgendaItem.Event) {
                item.endAt.toLocalDate() ?: state.selectedDate
            } else state.selectedDate,
            endTime = if (item is AgendaItem.Event) {
                item.endAt.toLocalTime() ?: LocalTime.now()
            } else LocalTime.now(),
        )
    }
}