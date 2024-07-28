package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.EventRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem

class GetEventUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String): AgendaItem.Event? {
        return eventRepository.getEvent(eventId)
    }
}
