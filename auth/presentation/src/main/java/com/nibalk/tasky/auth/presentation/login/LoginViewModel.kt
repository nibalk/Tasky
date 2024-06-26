package com.nibalk.tasky.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.auth.domain.model.LoginRequestParams
import com.nibalk.tasky.auth.domain.usecase.LoginUserUseCase
import com.nibalk.tasky.auth.domain.usecase.ValidateEmailUseCase
import com.nibalk.tasky.auth.domain.usecase.ValidatePasswordUseCase
import com.nibalk.tasky.auth.presentation.R
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.onError
import com.nibalk.tasky.core.domain.util.onSuccess
import com.nibalk.tasky.core.presentation.utils.UiText
import com.nibalk.tasky.core.presentation.utils.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val loginUserUseCase: LoginUserUseCase
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
            state = state.copy(isLogin = true)
            loginUserUseCase(
                LoginRequestParams(
                    email = state.email.text.toString().trim(),
                    password = state.password.text.toString()
                )
            ).onSuccess {
                state = state.copy(isLogin = false)
                eventChannel.send(LoginEvent.LoginSuccess)
            }.onError { error ->
                state = state.copy(isLogin = false)
                if(error == DataError.Network.UNAUTHORIZED) {
                    eventChannel.send(LoginEvent.LoginError(
                        UiText.StringResource(R.string.auth_error_email_password_incorrect)
                    ))
                } else {
                    eventChannel.send(LoginEvent.LoginError(error.asUiText()))
                }
            }
        }
    }
}
