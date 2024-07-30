package com.nibalk.tasky.agenda.domain

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AgendaRepository {
    suspend fun getAgendas(
        selectedDate: LocalDate
    ): Flow<List<AgendaItem>>

    suspend fun fetchAgendasByDate(
        selectedDate: LocalDate
    ): EmptyResult<DataError>

    suspend fun fetchAllAgendas(): EmptyResult<DataError>
}
