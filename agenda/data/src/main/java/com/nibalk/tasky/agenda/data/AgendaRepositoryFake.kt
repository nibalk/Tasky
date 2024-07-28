package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.test.mock.AgendaSampleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate

class AgendaRepositoryFake() : AgendaRepository {

    override suspend fun getAgendas(selectedDate: LocalDate): Flow<List<AgendaItem>> {
        val filteredDataFlow: Flow<List<AgendaItem>> = flow {
            val filteredList = AgendaSampleData.allAgendas.filter { item ->
                item.startAt.toLocalDate() == selectedDate
            }
            emit(filteredList)
        }
        return filteredDataFlow
    }

    override suspend fun fetchAgendas(
        selectedDate: LocalDate
    ): EmptyResult<DataError>  {
        TODO("Not yet implemented")
    }
}
