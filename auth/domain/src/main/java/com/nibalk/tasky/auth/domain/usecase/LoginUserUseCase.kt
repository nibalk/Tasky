package com.nibalk.tasky.auth.domain.usecase

import com.nibalk.tasky.auth.domain.AuthRepository
import com.nibalk.tasky.auth.domain.model.LoginRequestParams
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result

class LoginUserUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        loginRequestParams: LoginRequestParams
    ) : Result<Unit, DataError.Network> {
        return authRepository.login(loginRequestParams)
    }
}
