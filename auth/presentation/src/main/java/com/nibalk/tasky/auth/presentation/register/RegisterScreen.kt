package com.nibalk.tasky.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError
import com.nibalk.tasky.auth.domain.utils.AuthDataValidator
import com.nibalk.tasky.auth.presentation.R
import com.nibalk.tasky.auth.presentation.components.AuthBackButton
import com.nibalk.tasky.core.presentation.components.TaskyActionButton
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.components.TaskyPasswordTextField
import com.nibalk.tasky.core.presentation.components.TaskyTextField
import com.nibalk.tasky.core.presentation.themes.CheckMarkIcon
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import com.nibalk.tasky.core.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onBackClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.uiEvent) { event ->
        when(event) {
            is RegisterEvent.RegistrationError -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.auth_is_successful,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is RegisterAction.OnBackClick -> {
                    onBackClick()
                }
                is RegisterAction.OnRegisterClick -> {
                    onSuccessfulRegistration()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    TaskyBackground(
        title = stringResource(id = R.string.auth_create_your_account),
        footer = {
            if (WindowInsets.ime.getBottom(LocalDensity.current) <= 0) {
                RegisterScreenFooter(onAction)
            }
        }
    ) {
        // Name Field
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        TaskyTextField(
            state = state.name,
            endIcon = if (state.nameError == null) {
                CheckMarkIcon
            } else null,
            hint = stringResource(id = R.string.auth_name),
            modifier = Modifier.fillMaxWidth(),
            error =
            when (state.nameError) {
                AuthDataValidateError.NameError.EMPTY -> {
                    stringResource(id = R.string.auth_must_enter_name)
                }
                AuthDataValidateError.NameError.INVALID_LENGTH -> {
                    stringResource(
                        id = R.string.auth_must_be_between_x_to_y_characters,
                        AuthDataValidator.NAME_MIN_LENGTH,
                        AuthDataValidator.NAME_MAX_LENGTH,
                    )
                }
                else -> null
            }
        )
        // Email Field
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        TaskyTextField(
            state = state.email,
            endIcon = if (state.emailError == null) {
                CheckMarkIcon
            } else null,
            hint = stringResource(id = R.string.auth_email),
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth(),
            error = when (state.emailError) {
                AuthDataValidateError.EmailError.EMPTY -> {
                    stringResource(id = R.string.auth_must_enter_email)
                }
                AuthDataValidateError.EmailError.INVALID_FORMAT -> {
                    stringResource(id = R.string.auth_must_be_a_valid_email)
                }
                else -> null
            }
        )
        // Password Field
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        TaskyPasswordTextField(
            state = state.password,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = {
                onAction(RegisterAction.OnTogglePasswordVisibilityClick)
            },
            hint = stringResource(id = R.string.auth_password),
            modifier = Modifier.fillMaxWidth(),
            error = when(state.passwordError) {
                AuthDataValidateError.PasswordError.EMPTY -> {
                    stringResource(id = R.string.auth_must_enter_password)
                }
                AuthDataValidateError.PasswordError.TOO_SHORT -> {
                    stringResource(
                        id = R.string.auth_at_least_x_characters,
                        AuthDataValidator.PASSWORD_MIN_LENGTH
                    )
                }
                AuthDataValidateError.PasswordError.NO_DIGIT -> {
                    stringResource(id = R.string.auth_at_least_one_number)
                }
                AuthDataValidateError.PasswordError.NO_LOWERCASE -> {
                    stringResource(id = R.string.auth_at_least_one_lowercase_char)
                }
                AuthDataValidateError.PasswordError.NO_UPPERCASE -> {
                    stringResource(id = R.string.auth_at_least_one_uppercase_char)
                }
                else -> {
                    null
                }
            }
        )
        // Action Button
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))
        TaskyActionButton(
            text = stringResource(id = R.string.auth_get_started).uppercase(),
            isLoading = state.isRegistering,
            enabled = state.canRegister,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onAction(RegisterAction.OnRegisterClick)
            }
        )
    }
}

@Composable
private fun RegisterScreenFooter(
    onAction: (RegisterAction) -> Unit
) {
    // Back Button
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraLarge))
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        AuthBackButton(
            onClick = {
                onAction(RegisterAction.OnBackClick)
            },
            modifier = Modifier
                .align(Alignment.Start)
                .padding(MaterialTheme.spacing.spaceMedium)
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    TaskyTheme {
        RegisterScreen(
            state = RegisterState(
                passwordError = AuthDataValidateError.PasswordError.TOO_SHORT
            ),
            onAction = {}
        )
    }
}
