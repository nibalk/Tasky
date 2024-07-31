package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.EventRepository

class DeleteEventUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String) {
        return eventRepository.deleteEvent(eventId)
    }
}
