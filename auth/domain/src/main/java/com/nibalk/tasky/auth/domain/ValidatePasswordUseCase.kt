package com.nibalk.tasky.auth.domain

import com.nibalk.tasky.auth.domain.utils.AuthDataValidator
import com.nibalk.tasky.auth.domain.utils.PasswordValidationState

class ValidatePasswordUseCase {
    operator fun invoke(password: String): PasswordValidationState {
        val hasMinLength = password.length >= AuthDataValidator.PASSWORD_MIN_LENGTH
        val hasDigit = password.any { it.isDigit() }
        val hasLowerCaseCharacter = password.any { it.isLowerCase() }
        val hasUpperCaseCharacter = password.any { it.isUpperCase() }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasNumericCharacter = hasDigit,
            hasLowerCaseCharacter = hasLowerCaseCharacter,
            hasUpperCaseCharacter = hasUpperCaseCharacter
        )
    }
}
