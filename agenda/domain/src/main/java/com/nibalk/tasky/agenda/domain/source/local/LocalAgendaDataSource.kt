package com.nibalk.tasky.agenda.domain.source.local

import com.nibalk.tasky.agenda.domain.model.AgendaItems
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

interface LocalAgendaDataSource {
    suspend fun fetchAndStoreAgendas(agendas: AgendaItems): EmptyResult<DataError.Local>
}
