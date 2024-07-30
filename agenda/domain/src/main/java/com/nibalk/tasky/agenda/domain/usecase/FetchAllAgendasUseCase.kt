package com.nibalk.tasky.agenda.domain.usecase

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

class FetchAllAgendasUseCase(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(): EmptyResult<DataError>  {
        return agendaRepository.fetchAllAgendas()
    }
}
