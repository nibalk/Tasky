@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.nibalk.tasky.auth.domain.utils.PasswordValidationState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isValidEmail: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isLogin: Boolean = false,
    val canLogin: Boolean = false
)
