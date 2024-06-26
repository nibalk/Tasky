package com.nibalk.tasky.auth.presentation.login

import com.nibalk.tasky.core.presentation.utils.UiText

sealed interface LoginEvent {
    data object LoginSuccess: LoginEvent
    data class LoginError(val error: UiText): LoginEvent
}
