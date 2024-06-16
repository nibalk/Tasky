@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.auth.domain.utils.AuthDataValidator
import com.nibalk.tasky.auth.domain.utils.PasswordValidationState
import com.nibalk.tasky.auth.presentation.R
import com.nibalk.tasky.core.presentation.components.TaskyActionButton
import com.nibalk.tasky.core.presentation.components.TaskyBackButton
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.components.TaskyPasswordTextField
import com.nibalk.tasky.core.presentation.components.TaskyTextField
import com.nibalk.tasky.core.presentation.themes.CheckMarkIcon
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    TaskyBackground(
        title = stringResource(id = R.string.auth_create_your_account),
    ) {
        // Name Field
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        TaskyTextField(
            state = state.name,
            endIcon = if (state.isValidName) {
                CheckMarkIcon
            } else null,
            hint = stringResource(id = R.string.auth_name),
            modifier = Modifier.fillMaxWidth(),
            error = if(!state.isValidName) {
                stringResource(
                    id = R.string.auth_must_be_between_x_to_y_characters,
                    AuthDataValidator.NAME_MIN_LENGTH,
                    AuthDataValidator.NAME_MAX_LENGTH,
                )
            } else {
                null
            }
        )
        // Email Field
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        TaskyTextField(
            state = state.email,
            endIcon = if (state.isValidEmail) {
                CheckMarkIcon
            } else null,
            hint = stringResource(id = R.string.auth_email),
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth(),
            error = if(!state.isValidEmail) {
                stringResource(
                    id = R.string.auth_must_be_a_valid_email,
                    AuthDataValidator.PASSWORD_MIN_LENGTH
                )
            } else {
                null
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
            error = if(!state.passwordValidationState.hasMinLength) {
                stringResource(
                    id = R.string.auth_at_least_x_characters,
                    AuthDataValidator.PASSWORD_MIN_LENGTH
                )
            } else if (!state.passwordValidationState.hasNumericCharacter) {
                stringResource(
                    id = R.string.auth_at_least_one_number,
                )
            } else if (!state.passwordValidationState.hasLowerCaseCharacter) {
                stringResource(
                    id = R.string.auth_at_least_one_lowercase_char,
                )
            } else if (!state.passwordValidationState.hasUpperCaseCharacter) {
                stringResource(
                    id = R.string.auth_at_least_one_uppercase_char,
                )
            } else {
                null
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
        // Back Button
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraLarge))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            TaskyBackButton(
                isVisible = WindowInsets.ime.getBottom(LocalDensity.current) <= 0,
                onClick = {
                    onAction(RegisterAction.OnRegisterClick)
                },
                modifier = Modifier
                    .padding(
                        bottom = with(LocalDensity.current) {
                            WindowInsets.navigationBars.getBottom(this).toDp()
                        },
                    )
            )
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    TaskyTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumericCharacter = true,
                )
            ),
            onAction = {}
        )
    }
}
