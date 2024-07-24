package com.nibalk.tasky.agenda.data.remote.api

import com.nibalk.tasky.agenda.data.remote.dto.ReminderDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ReminderApi {
    companion object {
        const val REMINDER_ENDPOINT = "/reminder"
        const val REMINDER_QUERY_PARAM = "reminderId"
    }

    @POST(REMINDER_ENDPOINT)
    suspend fun createReminder(
        @Body body: ReminderDto
    ): Response<Unit>

    @PUT(REMINDER_ENDPOINT)
    suspend fun updateReminder(
        @Body body: ReminderDto
    ): Response<Unit>

    @GET(REMINDER_ENDPOINT)
    suspend fun getReminder(
        @Query(REMINDER_QUERY_PARAM) id: String
    ): Response<ReminderDto>

    @DELETE(REMINDER_ENDPOINT)
    suspend fun deleteReminder(
        @Query(REMINDER_QUERY_PARAM) id: String
    ): Response<Unit>
}
