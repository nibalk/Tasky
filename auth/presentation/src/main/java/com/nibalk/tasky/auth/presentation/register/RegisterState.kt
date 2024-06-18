@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError

data class RegisterState(
    val name: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val nameError: AuthDataValidateError.NameError? = null,
    val emailError: AuthDataValidateError.EmailError? = null,
    val passwordError: AuthDataValidateError.PasswordError? = null,
    val isPasswordVisible: Boolean = false,
    val isRegistering: Boolean = false,
) {
    val canRegister: Boolean
        get() = nameError == null &&
            emailError == null &&
            passwordError == null &&
            !isRegistering
}
