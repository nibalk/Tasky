package com.nibalk.tasky.auth.presentation.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.BackIcon
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun AuthBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
    ) {
        Icon(
            imageVector = BackIcon,
            contentDescription = stringResource(id = R.string.content_description_back),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AuthBackButtonPreview() {
    TaskyTheme {
        AuthBackButton(
            onClick = {}
        )
    }
}
