package com.nibalk.tasky.agenda.data.remote.api

import com.nibalk.tasky.agenda.data.remote.dto.AgendaDeletedItemsDto
import com.nibalk.tasky.agenda.data.remote.dto.AgendaItemsDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AgendaApi {
    companion object {
        const val AGENDA_SYNC_ENDPOINT = "/syncAgenda"
        const val AGENDA_FULL_ENDPOINT = "/fullAgenda"
        const val AGENDA_QUERY_ENDPOINT = "/agenda"
        const val AGENDA_TIME_ZONE_QUERY_PARAM = "timezone"
        const val AGENDA_TIME_QUERY_PARAM = "time"
    }

    @POST(AGENDA_SYNC_ENDPOINT)
    suspend fun syncAgendaItems(
        @Body body: AgendaDeletedItemsDto
    ): Response<Unit>

    @GET(AGENDA_FULL_ENDPOINT)
    suspend fun getFullAgenda(): Response<AgendaItemsDto>

    @GET(AGENDA_QUERY_ENDPOINT)
    suspend fun getAgendaItems(
        @Query(AGENDA_TIME_QUERY_PARAM) time: Long
    ): Response<AgendaItemsDto>
}
