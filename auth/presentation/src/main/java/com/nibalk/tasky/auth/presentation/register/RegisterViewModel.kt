@file:OptIn(ExperimentalFoundationApi::class)

package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.auth.domain.usecases.ValidateEmailUseCase
import com.nibalk.tasky.auth.domain.usecases.ValidateNameUseCase
import com.nibalk.tasky.auth.domain.usecases.ValidatePasswordUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
): ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val eventChannel = Channel<RegisterEvent>()
    val uiEvent: Flow<RegisterEvent> = eventChannel.receiveAsFlow()

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
                register()
            }
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            RegisterAction.OnBackClick -> {
                //TODO: Go back to login screen
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            eventChannel.send(RegisterEvent.RegistrationSuccess)
            // TODO: Make the API call here
        }
    }
}
