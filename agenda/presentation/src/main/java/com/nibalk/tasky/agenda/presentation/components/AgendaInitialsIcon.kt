package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.core.presentation.themes.TaskyBlue
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun AgendaInitialsIcon(
    modifier: Modifier = Modifier,
    userInitials: String,
    onIconClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(MaterialTheme.spacing.spaceLarge)
            .background(color = TaskyLightBlue, shape = CircleShape)
            .clickable { onIconClicked() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = userInitials,
            color = TaskyBlue,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun AgendaInitialsIconPreview() {
    TaskyTheme {
        AgendaInitialsIcon(
            userInitials = "AB",
            onIconClicked = {}
        )
    }
}
