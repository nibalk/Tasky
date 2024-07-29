package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import java.time.LocalDate

class FetchAgendasUseCase(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(
        selectedDate: LocalDate
    ): EmptyResult<DataError>  {
        return agendaRepository.fetchAgendas(selectedDate)
    }
}
