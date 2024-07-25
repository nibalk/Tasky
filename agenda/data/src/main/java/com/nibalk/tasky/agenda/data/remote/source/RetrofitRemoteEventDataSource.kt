package com.nibalk.tasky.agenda.data.remote.source

import android.content.Context
import android.net.Uri
import com.nibalk.tasky.agenda.data.remote.api.EventApi
import com.nibalk.tasky.agenda.data.remote.mapper.toAgendaItemEvent
import com.nibalk.tasky.agenda.data.remote.mapper.toEventDto
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.agenda.domain.model.EventPhoto
import com.nibalk.tasky.agenda.domain.source.remote.RemoteEventDataSource
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.data.utils.getCompressedByteArray
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class RetrofitRemoteEventDataSource(
    private val eventApi: EventApi,
    private val context: Context
) : RemoteEventDataSource {
    override suspend fun getEvent(
        eventId: String
    ): Result<AgendaItem.Event?, DataError.Network> {
        val response = safeCall {
            eventApi.getEvent(eventId)
        }
        return response.map { eventDto ->
            eventDto?.toAgendaItemEvent()
        }
    }

    override suspend fun createEvent(
        event: AgendaItem.Event
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            eventApi.createEvent(
                body = MultipartBody.Part.createFormData(
                    name = "create_event_request",
                    value = Json.encodeToString(event.toEventDto())
                ),
                photos = event.photos
                    .filterIsInstance<EventPhoto.Local>()
                    .mapIndexedNotNull { index, eventPhoto ->
                        val bytes = Uri.parse(eventPhoto.localUri).getCompressedByteArray(context)
                        MultipartBody.Part.createFormData(
                            name = "photo$index",
                            filename = eventPhoto.key,
                            body = bytes?.toRequestBody() ?: return@mapIndexedNotNull null
                        )
                    }
            )
        }
        return response.asEmptyDataResult()
    }

    override suspend fun updateEvent(
        event: AgendaItem.Event
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            eventApi.updateEvent(
                body = MultipartBody.Part.createFormData(
                    name = "update_event_request",
                    value = Json.encodeToString(event.toEventDto())
                ),
                photos = event.photos
                    .filterIsInstance<EventPhoto.Local>()
                    .mapIndexedNotNull { index, eventPhoto ->
                        val bytes = Uri.parse(eventPhoto.localUri).getCompressedByteArray(context)
                        MultipartBody.Part.createFormData(
                            name = "photo$index",
                            filename = eventPhoto.key,
                            body = bytes?.toRequestBody() ?: return@mapIndexedNotNull null
                        )
                    }
            )
        }
        return response.asEmptyDataResult()
    }

    override suspend fun deleteEvent(
        eventId: String
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            eventApi.deleteEvent(eventId)
        }
        return response.asEmptyDataResult()
    }

}
