package com.nibalk.tasky.agenda.domain.source.remote

import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result

interface RemoteAgendaDataSource {
    suspend fun getEvent(
        eventId: String
    ): Result<AgendaItem.Event?, DataError.Network>

    suspend fun createEvent(
        event: AgendaItem.Event
    ): EmptyResult<DataError.Network>

    suspend fun updateEvent(
        event: AgendaItem.Event
    ): EmptyResult<DataError.Network>

    suspend fun deleteEvent(
        eventId: String
    ): EmptyResult<DataError.Network>
}
