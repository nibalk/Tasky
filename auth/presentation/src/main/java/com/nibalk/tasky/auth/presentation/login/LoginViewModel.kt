package com.nibalk.tasky.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.auth.domain.usecase.ValidateEmailUseCase
import com.nibalk.tasky.auth.domain.usecase.ValidatePasswordUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val eventChannel = Channel<LoginEvent>()
    val uiEvent: Flow<LoginEvent> = eventChannel.receiveAsFlow()

    init {
        combine(
            snapshotFlow { state.email.text },
            snapshotFlow { state.password.text }
        ) { email, password ->
            state = state.copy(
                emailError = validateEmailUseCase(email.toString()),
                passwordError = validatePasswordUseCase(password.toString()),
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.OnLoginClick -> {
                login()
            }
            LoginAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }
            LoginAction.OnSignUpClick -> {
                //TODO: Go to register screen
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            eventChannel.send(LoginEvent.LoginSuccess)
            // TODO: Make the API call here
        }
    }
}
