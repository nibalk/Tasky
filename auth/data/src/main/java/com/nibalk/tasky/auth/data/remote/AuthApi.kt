package com.nibalk.tasky.auth.data.remote

import com.nibalk.tasky.auth.data.remote.dto.LoginRequestDto
import com.nibalk.tasky.auth.data.remote.dto.RegisterRequestDto
import com.nibalk.tasky.core.data.auth.AuthInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/register")
    suspend fun register(
        @Body body: RegisterRequestDto
    ): Response<Void>

    @POST("/register")
    suspend fun login(
        @Body body: LoginRequestDto
    ): Response<AuthInfoDto>
}
