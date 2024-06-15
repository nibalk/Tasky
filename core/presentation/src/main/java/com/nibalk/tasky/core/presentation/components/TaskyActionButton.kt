package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.test.isEnabled
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyBlack
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyDarkOrange
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.tertiary,
            disabledContentColor = TaskyBlack,
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        TaskyActionButtonContent(text, isLoading, isOutlined = false)
    }
}

@Composable
fun TaskyOutlinedActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        border = BorderStroke(
            width = 1.0.dp,
            color = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier
            .height(IntrinsicSize.Min)
    ) {
        TaskyActionButtonContent(text, isLoading, isOutlined = true)
    }
}

@Composable
private fun TaskyActionButtonContent(
    text: String,
    isLoading: Boolean,
    isOutlined: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.spaceSmall),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            modifier = Modifier
                .alpha(if(isLoading) 0f else 1f),
            fontWeight = FontWeight.Medium
        )
        LinearProgressIndicator(
            modifier = Modifier
                .size(15.dp)
                .alpha(if (isLoading) 1f else 0f),
            color = if (isOutlined) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.onPrimary
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyActionButtonEnabledPreview() {
    TaskyTheme {
        TaskyActionButton(
            text = "Sign in",
            isLoading = false,
            enabled = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun TaskyActionButtonDisabledPreview() {
    TaskyTheme {
        TaskyActionButton(
            text = "Sign in",
            isLoading = false,
            enabled = false,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyOutlinedActionButtonPreview() {
    TaskyTheme {
        TaskyOutlinedActionButton(
            text = "Sign up",
            isLoading = false,
            onClick = {}
        )
    }
}
