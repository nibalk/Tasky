package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetAgendasUseCase(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(
        selectedDate: LocalDate
    ): Flow<List<AgendaItem>> {
        return agendaRepository.getAgendas(selectedDate)
    }
}
