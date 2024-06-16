package com.nibalk.tasky.auth.presentation.register

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityClick: RegisterAction
    data object OnRegisterClick: RegisterAction
    data object OnBackClick: RegisterAction
}
