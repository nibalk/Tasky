package com.nibalk.tasky.auth.domain.usecase

import com.nibalk.tasky.auth.domain.AuthRepository
import com.nibalk.tasky.auth.domain.model.RegisterRequestParams
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result

class RegisterUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerParams: RegisterRequestParams) : Result<Unit, DataError.Network> {
        return authRepository.register(registerParams)
    }
}
