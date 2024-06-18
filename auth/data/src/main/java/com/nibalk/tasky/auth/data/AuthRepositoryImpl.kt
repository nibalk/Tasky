package com.nibalk.tasky.auth.data

import com.nibalk.tasky.auth.data.remote.AuthApi
import com.nibalk.tasky.auth.data.remote.dto.RegisterRequestDto
import com.nibalk.tasky.auth.domain.AuthRepository
import com.nibalk.tasky.auth.domain.model.RegisterRequestParams
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.asEmptyDataResult

class AuthRepositoryImpl(
    private val authApi: AuthApi
): AuthRepository {

    override suspend fun register(
        registerRequestParams: RegisterRequestParams
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            authApi.register(
                body = RegisterRequestDto(
                    userName = registerRequestParams.name,
                    userEmail = registerRequestParams.email,
                    userPassword = registerRequestParams.password
                )
            )
        }
        return response.asEmptyDataResult()
    }
}
