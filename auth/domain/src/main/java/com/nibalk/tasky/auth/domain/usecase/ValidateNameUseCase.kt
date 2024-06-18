package com.nibalk.tasky.auth.domain.usecase

import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError
import com.nibalk.tasky.auth.domain.utils.AuthDataValidator

class ValidateNameUseCase() {
    operator fun invoke(name: String): AuthDataValidateError.NameError? {
        return when {
            name.isEmpty() -> {
                AuthDataValidateError.NameError.EMPTY
            }
            name.length !in AuthDataValidator.NAME_MIN_LENGTH..AuthDataValidator.NAME_MAX_LENGTH -> {
                AuthDataValidateError.NameError.INVALID_LENGTH
            }
            else -> null
        }
    }
}


