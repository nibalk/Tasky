@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.auth.domain.utils.AuthDataValidateError
import com.nibalk.tasky.auth.domain.utils.AuthDataValidator
import com.nibalk.tasky.auth.presentation.R
import com.nibalk.tasky.auth.presentation.components.AuthClickableText
import com.nibalk.tasky.core.presentation.components.TaskyActionButton
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.components.TaskyPasswordTextField
import com.nibalk.tasky.core.presentation.components.TaskyTextField
import com.nibalk.tasky.core.presentation.themes.CheckMarkIcon
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    onSignUpClick: () -> Unit,
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is LoginAction.OnSignUpClick -> {
                    onSignUpClick()
                }
                is LoginAction.OnLoginClick -> {
                    onSuccessfulLogin()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    TaskyBackground(
        title = stringResource(id = R.string.auth_welcome),
        footer = {
            if (WindowInsets.ime.getBottom(LocalDensity.current) <= 0) {
                LoginScreenFooter(onAction)
            }
        }
    ) {
        // Email Field
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        TaskyTextField(
            state = state.email,
            endIcon = if (state.emailError == null) {
                CheckMarkIcon
            } else null,
            hint = stringResource(id = R.string.auth_email),
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Email,
            error = when (state.emailError) {
                AuthDataValidateError.EmailError.EMPTY -> {
                    stringResource(
                        id = R.string.auth_must_enter_email,
                        AuthDataValidator.PASSWORD_MIN_LENGTH
                    )
                }
                AuthDataValidateError.EmailError.INVALID_FORMAT -> {
                    stringResource(
                        id = R.string.auth_must_be_a_valid_email,
                        AuthDataValidator.PASSWORD_MIN_LENGTH
                    )
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
                onAction(LoginAction.OnTogglePasswordVisibilityClick)
            },
            hint = stringResource(id = R.string.auth_password),
            modifier = Modifier.fillMaxWidth(),
        )
        // Action Button
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))
        TaskyActionButton(
            text = stringResource(id = R.string.auth_get_started).uppercase(),
            isLoading = state.isLogin,
            enabled = state.canLogin,
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onAction(LoginAction.OnLoginClick)
            }
        )
    }
}

@Composable
private fun LoginScreenFooter(
    onAction: (LoginAction) -> Unit
) {
    // Sign-up Link
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceExtraLarge))
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        AuthClickableText(
            onClick = {
                onAction(LoginAction.OnSignUpClick)
            },
            mainText = stringResource(id = R.string.auth_no_account).uppercase() + " ",
            annotatedText = stringResource(id = R.string.auth_sign_up).uppercase(),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(MaterialTheme.spacing.spaceMedium)
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    TaskyTheme {
        LoginScreen(
            state = LoginState(
                passwordError = AuthDataValidateError.PasswordError.NO_DIGIT
            ),
            onAction = {}
        )
    }
}
