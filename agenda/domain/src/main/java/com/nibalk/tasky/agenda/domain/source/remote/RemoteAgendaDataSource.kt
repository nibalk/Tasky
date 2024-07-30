package com.nibalk.tasky.agenda.domain.source.remote

import com.nibalk.tasky.agenda.domain.model.AgendaDeletedItems
import com.nibalk.tasky.agenda.domain.model.AgendaItems
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result

interface RemoteAgendaDataSource {
    suspend fun syncAgendaItems(
        deletedItems: AgendaDeletedItems
    ): EmptyResult<DataError.Network>

    suspend fun getFullAgenda(): Result<AgendaItems?, DataError.Network>

    suspend fun getAgendaItems(time: Long): Result<AgendaItems?, DataError.Network>
}
