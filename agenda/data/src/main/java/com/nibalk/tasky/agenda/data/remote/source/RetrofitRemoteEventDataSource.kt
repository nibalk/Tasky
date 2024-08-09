package com.nibalk.tasky.agenda.data.remote.source

import android.content.Context
import android.net.Uri
import com.nibalk.tasky.agenda.data.remote.api.EventApi
import com.nibalk.tasky.agenda.data.remote.mapper.toAgendaItemEvent
import com.nibalk.tasky.agenda.data.remote.mapper.toEventRequestForCreateDto
import com.nibalk.tasky.agenda.data.remote.mapper.toEventRequestForUpdateDto
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
import timber.log.Timber

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
    ): Result<AgendaItem.Event?, DataError.Network> {
        val formData = Json.encodeToString(event.toEventRequestForCreateDto())
        val photoData = event.photos
            .filterIsInstance<EventPhoto.Local>()
            .mapIndexedNotNull { index, eventPhoto ->
                val bytes = Uri.parse(eventPhoto.localUri).getCompressedByteArray(context)
                MultipartBody.Part.createFormData(
                    name = "photo$index",
                    filename = eventPhoto.key + ".jpg",
                    body = bytes?.toRequestBody() ?: return@mapIndexedNotNull null
                )
            }
        Timber.d("[OfflineFirst-SaveEvent] REMOTE | Create formData = %s", formData)
        Timber.d("[OfflineFirst-SaveEvent] REMOTE | Create photoData = %s", photoData)

        val response = safeCall {
            eventApi.createEvent(
                body = MultipartBody.Part.createFormData(
                    name = "create_event_request",
                    value = formData
                ),
                photos = photoData,
            )
        }

        Timber.d("[OfflineFirst-SaveItem] REMOTE | Creating EVENT (%s)", event.id)
        return response.map { eventDto ->
            eventDto?.toAgendaItemEvent()
        }
    }

    override suspend fun updateEvent(
        event: AgendaItem.Event
    ): Result<AgendaItem.Event?, DataError.Network> {
        Timber.d("[OfflineFirst-ImageIssue] REMOTE | Update EVENT (%s)", event.photos)
        val formData = Json.encodeToString(event.toEventRequestForUpdateDto())
        val nextIndex = event.photos
            .filterIsInstance<EventPhoto.Remote>()
            .size
        Timber.d("[OfflineFirst-ImageIssue] LOCAL | nextIndex (%s)", nextIndex)

        val photoData = event.photos
            .filterIsInstance<EventPhoto.Local>()
            .mapIndexedNotNull { index, eventPhoto ->
                val bytes = Uri.parse(eventPhoto.localUri).getCompressedByteArray(context)
                Timber.d("[OfflineFirst-ImageIssue] LOCAL | index (%s)", (nextIndex+index))
                Timber.d("[OfflineFirst-ImageIssue] LOCAL | eventPhoto (%s)", eventPhoto)
                Timber.d("[OfflineFirst-ImageIssue] LOCAL | bytes (%s)", bytes)
                MultipartBody.Part.createFormData(
                    name = "photo${index + nextIndex}",
                    filename = eventPhoto.key + ".jpg",
                    body = bytes?.toRequestBody() ?: return@mapIndexedNotNull null
                )
            }
        Timber.d("[OfflineFirst-SaveEvent] REMOTE | Update formData = %s", formData)
        Timber.d("[OfflineFirst-SaveEvent] REMOTE | Update photoData = %s", photoData)

        val response = safeCall {
            eventApi.updateEvent(
                body = MultipartBody.Part.createFormData(
                    name = "update_event_request",
                    value = formData
                ),
                photos = photoData
            )
        }

        Timber.d("[OfflineFirst-SaveItem] REMOTE | Updating EVENT (%s)", event.id)
        Timber.d("[OfflineFirst-SaveItem] REMOTE | Updating EVENT response (%s)", response)
        return response.map { eventDto ->
            Timber.d("[OfflineFirst-SaveItem] REMOTE | Updating EVENT eventDto (%s)", eventDto)
            eventDto?.toAgendaItemEvent()
        }
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
