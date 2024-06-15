@file:OptIn(ExperimentalFoundationApi::class)

package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterViewModel(
    private val userDataValidator: UserDataValidator
): ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    init {
        state.email.textAsFlow()
            .onEach { name ->
                val isValidName = userDataValidator.isValidName(name.toString())
                state = state.copy(
                    isValidName = isValidName,
                    canRegister = isValidName &&
                        state.isEmailValid &&
                        state.passwordValidationState.isValidPassword &&
                        !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.email.textAsFlow()
            .onEach { email ->
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid = isValidEmail,
                    canRegister = state.isValidName &&
                        isValidEmail &&
                        state.passwordValidationState.isValidPassword &&
                        !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isValidName &&
                        state.isEmailValid &&
                        passwordValidationState.isValidPassword &&
                        !state.isRegistering
                )
            }
            .launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {

    }
}
