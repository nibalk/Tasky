package com.nibalk.tasky.agenda.presentation.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.test.mock.AgendaSampleData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventViewModel(
    private val agendaArgs: AgendaArgs
): ViewModel() {
    var state by mutableStateOf(EventState())
        private set

    private val eventChannel = Channel<EventState>()
    val uiEvent: Flow<EventState> = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            state = state.copy(
                isEditingMode = agendaArgs.isEditable,
                selectedDate = agendaArgs.selectedDate ?: LocalDate.now(),
                agendaId = agendaArgs.agendaId.orEmpty(),
                agendaItem = AgendaSampleData.allAgendas.find {
                    it is AgendaItem.Event && it.id == agendaArgs.agendaId
                } as? AgendaItem.Event
            )
        }
    }

    fun onAction(action: EventAction) {

    }
}
