package com.nibalk.tasky.agenda.data.remote.api

import com.nibalk.tasky.agenda.data.remote.dto.EventAttendeeResponseDto
import com.nibalk.tasky.agenda.data.remote.dto.EventDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface EventApi {
    companion object {
        const val EVENT_ENDPOINT = "/event"
        const val EVENT_QUERY_PARAM = "eventId"
        const val ATTENDEE_ENDPOINT = "/attendee"
        const val ATTENDEE_QUERY_PARAM = "email"
    }

    // Events

    @Multipart
    @POST(EVENT_ENDPOINT)
    suspend fun createEvent(
        @Part body: MultipartBody.Part,
        @Part photos: List<MultipartBody.Part>
    ): Response<EventDto>

    @Multipart
    @PUT(EVENT_ENDPOINT)
    suspend fun updateEvent(
        @Part body: MultipartBody.Part,
        @Part photos: List<MultipartBody.Part>
    ): Response<EventDto>

    @GET(EVENT_ENDPOINT)
    suspend fun getEvent(
        @Query(EVENT_QUERY_PARAM) id: String
    ): Response<EventDto>

    @DELETE(EVENT_ENDPOINT)
    suspend fun deleteEvent(
        @Query(EVENT_QUERY_PARAM) id: String
    ): Response<Unit>

    // Attendees

    @DELETE(ATTENDEE_ENDPOINT)
    suspend fun deleteAttendee(
        @Query(EVENT_QUERY_PARAM) eventId: String
    ): Response<Unit>

    @GET(ATTENDEE_ENDPOINT)
    suspend fun getAttendee(
        @Query(ATTENDEE_QUERY_PARAM) email: String
    ): Response<EventAttendeeResponseDto>
}
