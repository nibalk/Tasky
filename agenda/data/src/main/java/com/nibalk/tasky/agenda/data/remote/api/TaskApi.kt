package com.nibalk.tasky.agenda.data.remote.api

import com.nibalk.tasky.agenda.data.remote.dto.TaskDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TaskApi {
    companion object {
        const val TASK_ENDPOINT = "/task"
        const val TASK_QUERY_PARAM = "taskId"
    }

    @POST(TASK_ENDPOINT)
    suspend fun createTask(
        @Body body: TaskDto
    ): Response<Unit>

    @PUT(TASK_ENDPOINT)
    suspend fun updateTask(
        @Body body: TaskDto
    ): Response<Unit>

    @GET(TASK_ENDPOINT)
    suspend fun getTask(
        @Query(TASK_QUERY_PARAM) id: String
    ): Response<TaskDto>

    @DELETE(TASK_ENDPOINT)
    suspend fun deleteTask(
        @Query(TASK_QUERY_PARAM) id: String
    ): Response<Unit>
}
