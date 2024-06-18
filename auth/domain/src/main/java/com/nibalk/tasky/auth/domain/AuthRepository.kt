package com.nibalk.tasky.auth.domain

import com.nibalk.tasky.auth.domain.model.RegisterRequestParams
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(
        registerRequestParams: RegisterRequestParams
    ): EmptyResult<DataError.Network>
}
