@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val emailError: AuthDataValidateError.EmailError? = null,
    val passwordError: AuthDataValidateError.PasswordError? = null,
    val isPasswordVisible: Boolean = false,
    val isLogin: Boolean = false,
) {
    val canLogin: Boolean
        get() = emailError == null &&
            passwordError == null &&
            !isLogin
}
