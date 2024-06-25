package com.nibalk.tasky.auth.data

import com.nibalk.tasky.auth.data.remote.AuthApi
import com.nibalk.tasky.auth.data.remote.dto.LoginRequestDto
import com.nibalk.tasky.auth.data.remote.dto.RegisterRequestDto
import com.nibalk.tasky.auth.domain.AuthRepository
import com.nibalk.tasky.auth.domain.model.LoginRequestParams
import com.nibalk.tasky.auth.domain.model.RegisterRequestParams
import com.nibalk.tasky.core.data.auth.toAuthInfo
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.domain.auth.SessionStorage
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult
import com.nibalk.tasky.core.domain.util.asEmptyDataResult
import com.nibalk.tasky.core.domain.util.onSuccess

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sessionStorage: SessionStorage
): AuthRepository {

    override suspend fun login(
        loginRequestParams: LoginRequestParams
    ): EmptyResult<DataError.Network> {
        val response = safeCall {
            authApi.login(
                body = LoginRequestDto(
                    userEmail = loginRequestParams.email,
                    userPassword = loginRequestParams.password
                )
            )
        }
        response.onSuccess { result ->
            if (result != null) {
                sessionStorage.set(result.toAuthInfo())
            }
        }
        return response.asEmptyDataResult()
    }

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
