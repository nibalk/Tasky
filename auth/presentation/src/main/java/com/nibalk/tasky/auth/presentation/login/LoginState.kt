package com.nibalk.tasky.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val emailError: AuthDataValidateError.EmailError? = null,
    val passwordError: AuthDataValidateError.PasswordError? = null,
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
) {
    val canLogin: Boolean
        get() = emailError == null &&
            passwordError == null &&
            !isLoggingIn
}
