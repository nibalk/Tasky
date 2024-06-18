package com.nibalk.tasky.auth.presentation.register

import com.nibalk.tasky.core.presentation.utils.UiText

sealed interface RegisterEvent {
    data object RegistrationSuccess: RegisterEvent
    data class RegistrationError(val error: UiText): RegisterEvent
}
