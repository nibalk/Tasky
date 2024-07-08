package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import java.time.LocalDate

class GetAgendasUseCase(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(
        selectedDate: LocalDate
    ): Result<List<AgendaItem>, DataError.Network> {
        return agendaRepository.getAgendas(selectedDate)
    }
}
