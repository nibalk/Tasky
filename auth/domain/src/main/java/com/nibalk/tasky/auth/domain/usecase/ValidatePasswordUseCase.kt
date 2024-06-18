package com.nibalk.tasky.auth.domain.usecase

import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError
import com.nibalk.tasky.auth.domain.utils.AuthDataValidator

class ValidatePasswordUseCase {
    operator fun invoke(password: String): AuthDataValidateError.PasswordError? {
        return when {
            password.isEmpty() -> {
                AuthDataValidateError.PasswordError.EMPTY
            }
            password.length < AuthDataValidator.PASSWORD_MIN_LENGTH -> {
                AuthDataValidateError.PasswordError.TOO_SHORT
            }
            !(password.any { it.isDigit() }) -> {
                AuthDataValidateError.PasswordError.NO_DIGIT
            }
            !(password.any { it.isLowerCase() }) -> {
                AuthDataValidateError.PasswordError.NO_LOWERCASE
            }
            !(password.any { it.isUpperCase() }) -> {
                AuthDataValidateError.PasswordError.NO_UPPERCASE
            }
            else -> null
        }
    }
}

