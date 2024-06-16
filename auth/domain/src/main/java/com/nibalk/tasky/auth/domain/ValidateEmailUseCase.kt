package com.nibalk.tasky.auth.domain

import com.nibalk.tasky.auth.domain.utils.AuthPatternValidator

class ValidateEmailUseCase(
    private val authPatternValidator: AuthPatternValidator
) {
    operator fun invoke(email: String): Boolean {
        return authPatternValidator.matches(email.trim())
    }
}
