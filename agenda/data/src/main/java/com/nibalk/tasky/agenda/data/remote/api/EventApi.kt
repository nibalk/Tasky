package com.nibalk.tasky.agenda.data.remote.api

import com.nibalk.tasky.agenda.data.remote.dto.EventDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface EventApi {
    companion object {
        const val EVENT_ENDPOINT = "/event"
        const val EVENT_QUERY_PARAM = "eventId"
    }

    @POST(EVENT_ENDPOINT)
    suspend fun createEvent(
        @Body body: EventDto
    ): Response<Unit>

    @PUT(EVENT_ENDPOINT)
    suspend fun updateEvent(
        @Body body: EventDto
    ): Response<Unit>

    @GET(EVENT_ENDPOINT)
    suspend fun getEvent(
        @Query(EVENT_QUERY_PARAM) id: String
    ): Response<EventDto>

    @DELETE(EVENT_ENDPOINT)
    suspend fun deleteEvent(
        @Query(EVENT_QUERY_PARAM) id: String
    ): Response<Unit>
}
