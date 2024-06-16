@file:OptIn(ExperimentalFoundationApi::class)

package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.auth.domain.ValidateEmailUseCase
import com.nibalk.tasky.auth.domain.ValidateNameUseCase
import com.nibalk.tasky.auth.domain.ValidatePasswordUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
): ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    init {
        state.name.textAsFlow()
            .onEach { name ->
                val isValidName = validateNameUseCase(name.toString())
                state = state.copy(
                    isValidName = isValidName,
                    canRegister = isValidName &&
                        state.isValidEmail &&
                        state.passwordValidationState.isValidPassword &&
                        !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = validateEmailUseCase(email.toString())
                state = state.copy(
                    isValidEmail = isValidEmail,
                    canRegister = state.isValidName &&
                        isValidEmail &&
                        state.passwordValidationState.isValidPassword &&
                        !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = validatePasswordUseCase(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isValidName &&
                        state.isValidEmail &&
                        passwordValidationState.isValidPassword &&
                        !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when(action) {
            RegisterAction.OnRegisterClick -> {
                registerUser()
            }
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            RegisterAction.OnBackClick -> {
                //TODO: Go back to login screen
            }
            else -> Unit
        }
    }

    private fun registerUser() {
        //TODO: Register use API call
    }
}
