package com.nibalk.tasky.agenda.data.remote.source

import com.nibalk.tasky.agenda.data.remote.api.AgendaApi
import com.nibalk.tasky.agenda.data.remote.mapper.toAgendaDeletedItemsDto
import com.nibalk.tasky.agenda.data.remote.mapper.toAgendaItems
import com.nibalk.tasky.agenda.domain.model.AgendaDeletedItems
import com.nibalk.tasky.agenda.domain.model.AgendaItems
import com.nibalk.tasky.agenda.domain.source.remote.RemoteAgendaDataSource
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.map

class RetrofitRemoteAgendaDataSource(
    private val agendaApi: AgendaApi,
) : RemoteAgendaDataSource {

    override suspend fun syncAgendaItems(
        deletedItems: AgendaDeletedItems
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            agendaApi.syncAgendaItems(
                body = deletedItems.toAgendaDeletedItemsDto()
            )
        }
        return response.asEmptyDataResult()
    }

    override suspend fun getFullAgenda(): Result<AgendaItems?, DataError.Network> {
        val response = safeCall {
            agendaApi.getFullAgenda()
        }
        return response.map { agendaItemsDto ->
            agendaItemsDto?.toAgendaItems()
        }
    }

    override suspend fun getAgendaItems(
        timezone: String,
        time: Long
    ): Result<AgendaItems?, DataError.Network> {
        val response = safeCall {
            agendaApi.getAgendaItems(timezone, time)
        }
        return response.map { agendaItemsDto ->
            agendaItemsDto?.toAgendaItems()
        }
    }
}
