package com.nibalk.tasky.auth.domain.utils

import com.nibalk.tasky.core.domain.util.BaseError

sealed interface AuthDataValidateError: BaseError {
    enum class NameError: AuthDataValidateError {
        EMPTY,
        INVALID_LENGTH
    }
    enum class EmailError: AuthDataValidateError {
        EMPTY,
        INVALID_FORMAT
    }
    enum class PasswordError: AuthDataValidateError {
        EMPTY,
        TOO_SHORT,
        NO_DIGIT,
        NO_LOWERCASE,
        NO_UPPERCASE
    }
}
