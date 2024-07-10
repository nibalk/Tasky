package com.nibalk.tasky.agenda.domain

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import java.time.LocalDate

interface AgendaRepository {
    suspend fun getAgendas(
        selectedDate: LocalDate
    ): Result<List<AgendaItem>, DataError.Network>
}
