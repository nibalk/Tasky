package com.nibalk.tasky.auth.data.remote

import com.nibalk.tasky.auth.data.remote.dto.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/register")
    suspend fun register(
        @Body body: RegisterRequestDto
    ): Response<Void>
}
