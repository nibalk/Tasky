package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.EyeClosedIcon
import com.nibalk.tasky.core.presentation.themes.EyeOpenedIcon
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun TaskyPasswordTextField(
    state: TextFieldState,
    hint: String,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        BasicSecureTextField(
            state = state,
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else TextObfuscationMode.Hidden,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onTertiary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onTertiary),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.tertiary)
                .border(
                    width = 1.dp,
                    color = if (state.text.isNotEmpty() && !error.isNullOrEmpty()) {
                        MaterialTheme.colorScheme.error
                    } else if  (isFocused) {
                        MaterialTheme.colorScheme.onBackground
                    } else {
                        Color.Transparent
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (state.text.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }
                    IconButton(onClick = onTogglePasswordVisibility) {
                        Icon(
                            imageVector = if (!isPasswordVisible) {
                                EyeClosedIcon
                            } else EyeOpenedIcon,
                            contentDescription = if(isPasswordVisible) {
                                stringResource(id = R.string.content_description_show_password_text)
                            } else {
                                stringResource(id = R.string.content_description_hide_password_text)
                            },
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            if (state.text.isNotEmpty() && !error.isNullOrEmpty()) {
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyTextFieldPreview() {
    TaskyTheme {
        TaskyPasswordTextField(
            state = rememberTextFieldState(),
            hint = "Password",
            error = "Passwords doesn't match",
            modifier = Modifier
                .fillMaxWidth(),
            isPasswordVisible = false,
            onTogglePasswordVisibility = {}
        )
    }
}
