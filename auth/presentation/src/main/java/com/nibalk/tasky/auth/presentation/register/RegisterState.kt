@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.nibalk.tasky.auth.domain.PasswordValidationState

data class RegisterState(
    val name: TextFieldState = TextFieldState(),
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isValidName: Boolean = false,
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)
