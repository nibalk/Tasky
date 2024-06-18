package com.nibalk.tasky.auth.domain.usecase

import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError
import com.nibalk.tasky.auth.domain.utils.AuthPatternValidator

class ValidateEmailUseCase(
    private val authPatternValidator: AuthPatternValidator
) {
    operator fun invoke(email: String): AuthDataValidateError.EmailError? {
        return when {
            email.isEmpty() -> {
                AuthDataValidateError.EmailError.EMPTY
            }
            !authPatternValidator.matches(email.trim()) -> {
                AuthDataValidateError.EmailError.INVALID_FORMAT
            }
            else -> null
        }
    }
}
