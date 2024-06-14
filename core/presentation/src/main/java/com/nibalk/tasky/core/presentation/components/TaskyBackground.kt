package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyBackground(
    title: String? = null,
    header: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            val headerWeight = if (header == null) 1.5f else 0.5f
            val headerModifier = Modifier
                .fillMaxSize()
                .then(
                    Modifier.weight(headerWeight)
                )

            // Header
            if (!title.isNullOrEmpty()) {
                Box(
                    modifier = headerModifier,
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            } else if (header != null) {
                Box(modifier = headerModifier) {
                    header()
                }
            }

            // Content
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        Modifier.weight(10 - headerWeight)
                    )
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(modifier = Modifier.padding(MaterialTheme.spacing.spaceMedium)) {
                    content()
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskyBackgroundPreview() {
    TaskyTheme {
        TaskyBackground(
            title = "Welcome!"
        ) {
            Text("Hello World!")
        }
    }
}

@PreviewScreenSizes
@Composable
private fun TaskyBackgroundPreviewScreenSizes() {
    TaskyTheme {
        TaskyBackground(
            title = "Welcome!"
        ) {
            Text("Hello World!")
        }
    }
}
