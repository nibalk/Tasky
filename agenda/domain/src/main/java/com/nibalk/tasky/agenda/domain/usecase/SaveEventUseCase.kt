package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.EventRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

class SaveEventUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(
        event: AgendaItem.Event,
        isCreateNew: Boolean
    ): EmptyResult<DataError> {
        return if (isCreateNew) {
            eventRepository.createEvent(event)
        } else {
            eventRepository.updateEvent(event)
        }
    }
}
