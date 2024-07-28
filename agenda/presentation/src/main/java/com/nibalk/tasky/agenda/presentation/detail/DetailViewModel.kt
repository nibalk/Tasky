package com.nibalk.tasky.agenda.presentation.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.usecase.GetEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetReminderUseCase
import com.nibalk.tasky.agenda.domain.usecase.GetTaskUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveEventUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveReminderUseCase
import com.nibalk.tasky.agenda.domain.usecase.SaveTaskUseCase
import com.nibalk.tasky.agenda.presentation.model.AgendaArgs
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.agenda.presentation.model.EditorType
import com.nibalk.tasky.core.domain.util.onError
import com.nibalk.tasky.core.domain.util.onSuccess
import com.nibalk.tasky.core.presentation.utils.asUiText
import com.nibalk.tasky.test.mock.AgendaSampleData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.util.UUID

class DetailViewModel(
    private val agendaArgs: AgendaArgs,
    savedStateHandle: SavedStateHandle,
    private val getEventUseCase: GetEventUseCase,
    private val getTaskUseCase: GetTaskUseCase,
    private val getReminderUseCase: GetReminderUseCase,
    private val saveEventUseCase: SaveEventUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val saveReminderUseCase: SaveReminderUseCase,
): ViewModel() {

    var state by mutableStateOf(DetailState(
        details = when(AgendaType.valueOf(agendaArgs.agendaType)) {
            AgendaType.TASK -> AgendaItemDetails.Task()
            AgendaType.EVENT -> AgendaItemDetails.Event()
            AgendaType.REMINDER -> AgendaItemDetails.Reminder
        }
    ))
        private set

    private val eventChannel = Channel<DetailEvent>()
    val uiEvent: Flow<DetailEvent> = eventChannel.receiveAsFlow()

    init {
        val titleFromSavedState = savedStateHandle.get<String>(EditorType. TITLE. name)
        val descriptionFromSavedState = savedStateHandle.get<String>(EditorType.DESCRIPTION. name)

        viewModelScope.launch {
            state = state.copy(
                isEditingMode = agendaArgs.isEditable,
                selectedDate = agendaArgs.selectedDate ?: LocalDate.now(),
                agendaType = AgendaType.valueOf(agendaArgs.agendaType),
                agendaId = agendaArgs.agendaId.orEmpty(),
                title = titleFromSavedState ?: state.title,
                description = descriptionFromSavedState ?: state.description,
            )
            fetchAgendaItem()
        }
    }

    fun onAction(action: DetailAction) {
        when(action) {
            is DetailAction.OnStartDateSelected -> {
                state = state.copy(startDate = action.date)
            }
            is DetailAction.OnEndDateSelected -> {
                state = state.copy(
                    details = updateDetailsIfEvent { event ->
                        event.copy(endDate = action.date)
                    }
                )
            }
            is DetailAction.OnStartTimeSelected -> {
                state = state.copy(startTime = action.time)
            }
            is DetailAction.OnEndTimeSelected -> {
                state = state.copy(
                    details = updateDetailsIfEvent { event ->
                        event.copy(endTime = action.time)
                    }
                )
            }
            is DetailAction.OnIsEditableChanged -> {
                state = state.copy(isEditingMode = action.isEditable)
            }
            is DetailAction.OnNotificationDurationClicked -> {
                state =  state.copy(notificationDurationType = action.type)
            }
            is DetailAction.OnTitleEdited -> {
                state = state.copy(title = action.newTitle)
            }
            is DetailAction.OnDescriptionEdited -> {
                state = state.copy(description = action.newDescription)
            }
            is DetailAction.OnIsDoneCheckboxClicked -> {
                state = state.copy(
                    details = updateDetailsIfTask { task ->
                        task.copy(isDone = action.isDone)
                    }
                )
            }
            is DetailAction.OnSaveClicked -> {
                viewModelScope.launch { saveAgendaItem() }
            }
            else -> Unit
        }
    }

    // Fetch Agenda Item

    private suspend fun fetchAgendaItem() {
        when(state.agendaType) {
            AgendaType.EVENT -> invokeFetchEvent()
            AgendaType.TASK -> invokeFetchTask()
            AgendaType.REMINDER -> invokeFetchReminder()
        }
    }

    private suspend fun invokeFetchEvent() {
        val event = getEventUseCase.invoke(state.agendaId)
        state = event?.let { item ->
            state.copy(
                title = item.title,
                description = item.description,
                startDate = item.startAt.toLocalDate(),
                startTime = item.startAt.toLocalTime(),
                remindDate = item.remindAt.toLocalDate(),
                remindTime = item.remindAt.toLocalTime(),
                details = AgendaItemDetails.Event(
                    endDate = item.endAt.toLocalDate(),
                    endTime = item.endAt.toLocalTime(),
                    isHost = item.isHost,
                    hostId = item.hostId,
                    photos = item.photos,
                    attendees = item.attendees
                ),
            )
        } ?: state
    }

    private suspend fun invokeFetchTask() {
        val task =  getTaskUseCase.invoke(state.agendaId)
        state = task?.let { item ->
            state.copy(
                title = item.title,
                description = item.description,
                startDate = item.startAt.toLocalDate(),
                startTime = item.startAt.toLocalTime(),
                remindDate = item.remindAt.toLocalDate(),
                remindTime = item.remindAt.toLocalTime(),
                details = AgendaItemDetails.Task(
                    isDone = item.isDone
                ),
            )
        } ?: state
    }

    private suspend fun invokeFetchReminder() {
        val reminder = getReminderUseCase.invoke(state.agendaId)
        state = reminder?.let { item ->
            state.copy(
                title = item.title,
                description = item.description,
                startDate = item.startAt.toLocalDate(),
                startTime = item.startAt.toLocalTime(),
                remindDate = item.remindAt.toLocalDate(),
                remindTime = item.remindAt.toLocalTime(),
            )
        } ?: state
    }

    // Save Agenda Item

    private suspend fun saveAgendaItem() {
        when(state.agendaType) {
            AgendaType.EVENT -> invokeSaveEvent()
            AgendaType.TASK -> invokeSaveTask()
            AgendaType.REMINDER -> invokeSaveReminder()
        }
    }

    private suspend fun invokeSaveEvent() {
        val stateDetails = state.details.asEventDetails
        val randomIdIfNew = UUID.randomUUID().toString()
        Timber.d("[SaveAgendaItemLog] Event | agendaId = %s , randomIdIfNew = %s",
            state.agendaId, randomIdIfNew
        )

        saveEventUseCase.invoke(
            event = AgendaItem.Event(
                id = state.agendaId.ifEmpty { randomIdIfNew },
                title = state.title,
                description = state.description,
                startAt = state.startDate.atTime(state.startTime),
                remindAt = state.remindDate.atTime(state.remindTime),
                endAt = stateDetails.endDate.atTime(stateDetails.endTime),
                hostId = stateDetails.hostId,
                isHost = stateDetails.isHost,
                photos = stateDetails.photos,
                attendees = stateDetails.attendees
            )
        ).onError { error ->
            eventChannel.send(DetailEvent.DetailSaveError(error.asUiText()))
        }.onSuccess {
            eventChannel.send(DetailEvent.DetailSaveSuccess)
        }
    }

    private suspend fun invokeSaveTask() {
        val randomIdIfNew = UUID.randomUUID().toString()
        Timber.d("[SaveAgendaItemLog] Task | agendaId = %s , randomIdIfNew = %s",
            state.agendaId, randomIdIfNew
        )

        saveTaskUseCase.invoke(
            task = AgendaItem.Task(
                id = state.agendaId.ifEmpty { randomIdIfNew },
                title = state.title,
                description = state.description,
                startAt = state.startDate.atTime(state.startTime),
                remindAt = state.remindDate.atTime(state.remindTime),
                isDone = state.details.asTaskDetails.isDone
            )
        )
    }

    private suspend fun invokeSaveReminder() {
        val randomIdIfNew = UUID.randomUUID().toString()
        Timber.d("[SaveAgendaItemLog] Reminder | agendaId = %s , randomIdIfNew = %s",
            state.agendaId, randomIdIfNew
        )

        saveReminderUseCase.invoke(
            reminder = AgendaItem.Reminder(
                id = state.agendaId.ifEmpty { randomIdIfNew },
                title = state.title,
                description = state.description,
                startAt = state.startDate.atTime(state.startTime),
                remindAt = state.remindDate.atTime(state.remindTime),
            )
        )
    }

    // Utils

    private fun updateDetailsIfEvent(
        update: (AgendaItemDetails.Event) -> AgendaItemDetails.Event
    ): AgendaItemDetails {
        return when (val details = state.details) {
            is AgendaItemDetails.Event -> update(details)
            else -> details
        }
    }

    private fun updateDetailsIfTask(
        update: (AgendaItemDetails.Task) -> AgendaItemDetails.Task
    ): AgendaItemDetails {
        return when (val details = state.details) {
            is AgendaItemDetails.Task -> update(details)
            else -> details
        }
    }

    private val mockItem = when(AgendaType.valueOf(agendaArgs.agendaType)) { //TODO: Mocking to be deleted
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
}
