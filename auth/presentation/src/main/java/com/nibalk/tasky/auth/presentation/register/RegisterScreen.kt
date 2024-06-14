@file:OptIn(ExperimentalFoundationApi::class)
package com.nibalk.tasky.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.auth.domain.UserDataValidator
import com.nibalk.tasky.auth.presentation.R
import com.nibalk.tasky.core.presentation.components.TaskyBackground
import com.nibalk.tasky.core.presentation.components.TaskyPasswordRequirement
import com.nibalk.tasky.core.presentation.components.TaskyPasswordTextField
import com.nibalk.tasky.core.presentation.components.TaskyTextField
import com.nibalk.tasky.core.presentation.themes.CheckMarkIcon
import com.nibalk.tasky.core.presentation.themes.Inter
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel(),
) {
    RegisterScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    TaskyBackground(
        title = "Welcome!"
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.auth_create_your_account),
                style = MaterialTheme.typography.headlineMedium
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Inter,
                        color = TaskyGray
                    )
                ) {
                    append(stringResource(id = R.string.auth_no_account).uppercase() + " ")
                    pushStringAnnotation(
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.auth_sign_up).uppercase()
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Inter
                        )
                    ) {
                        append(stringResource(id = R.string.auth_sign_up))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(
                        tag = "clickable_text",
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )
            Spacer(modifier = Modifier.height(48.dp))
            TaskyTextField(
                state = state.email,
                endIcon = if (state.isEmailValid) {
                    CheckMarkIcon
                } else null,
                hint = stringResource(id = R.string.auth_email),
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            TaskyPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegisterAction.OnTogglePasswordVisibilityClick)
                },
                hint = stringResource(id = R.string.auth_password),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TaskyPasswordRequirement(
                text = stringResource(
                    id = R.string.auth_at_least_x_characters,
                    UserDataValidator.MIN_PASSWORD_LENGTH
                ),
                isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))
            TaskyPasswordRequirement(
                text = stringResource(
                    id = R.string.auth_at_least_one_number,
                ),
                isValid = state.passwordValidationState.hasNumericCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))
            TaskyPasswordRequirement(
                text = stringResource(
                    id = R.string.auth_contains_lowercase_char,
                ),
                isValid = state.passwordValidationState.hasLowerCaseCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))
            TaskyPasswordRequirement(
                text = stringResource(
                    id = R.string.auth_contains_uppercase_char,
                ),
                isValid = state.passwordValidationState.hasUpperCaseCharacter
            )
            Spacer(modifier = Modifier.height(32.dp))
//            TaskyActionButton(
//                text = stringResource(id = R.string.register_button),
//                isLoading = state.isRegistering,
//                enabled = state.canRegister,
//                modifier = Modifier.fillMaxWidth(),
//                onClick = {
//                    onAction(RegisterAction.OnRegisterClick)
//                }
//            )
        }
    }
}

//@Preview
//@Composable
//private fun RegisterScreenPreview() {
//    TaskyTheme {
//        RegisterScreen(
//            state = RegisterState(
//                passwordValidationState = PasswordValidationState(
//                    hasNumericCharacter = true,
//                )
//            ),
//            onAction = {}
//        )
//    }
//}
