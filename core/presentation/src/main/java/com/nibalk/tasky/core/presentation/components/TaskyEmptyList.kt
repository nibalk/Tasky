package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyEmptyList(
    modifier: Modifier = Modifier,
    contentColor: Color,
    displayMessage: String,
    displayIcon: Painter,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = displayIcon,
            contentDescription = "empty list",
            tint = contentColor,
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
        Text(
            text = displayMessage,
            style = MaterialTheme.typography.displayMedium,
            color = contentColor,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyEmptyListPreview() {
    TaskyTheme {
        TaskyEmptyList(
            displayMessage = "No agenda items for this day",
            displayIcon = painterResource(android.R.drawable.ic_menu_agenda),
            contentColor = TaskyDarkGray
        )
    }
}
