package com.nibalk.tasky.agenda.data

import com.nibalk.tasky.agenda.domain.AgendaRepository
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.test.mock.AgendaSampleData
import java.time.LocalDate

class AgendaRepositoryFake() : AgendaRepository {

    override suspend fun getAgendas(
        selectedDate: LocalDate
    ): Result<List<AgendaItem>, DataError.Network> {
        val filteredData = AgendaSampleData.allAgendas.filter { item ->
            item.startAt.toLocalDate() == selectedDate
        }
        return Result.Success(filteredData)
    }
}
