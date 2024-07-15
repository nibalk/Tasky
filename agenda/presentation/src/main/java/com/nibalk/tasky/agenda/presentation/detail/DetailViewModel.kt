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
                agendaItem = when(AgendaType.valueOf(agendaArgs.agendaType)) {
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
            )
        }
    }

    fun onAction(action: DetailAction) {

    }
}
