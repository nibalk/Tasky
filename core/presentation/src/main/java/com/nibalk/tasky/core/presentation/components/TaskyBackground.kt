package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyBackground(
    title: String? = null,
    contentHorizontalPadding: Dp = MaterialTheme.spacing.spaceMedium,
    header: (@Composable RowScope.() -> Unit)? = null,
    footer: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
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
            val footerWeight = if (footer == null) 0.1f else 0.5f
            val headerWeight = if (header == null) 1.5f else 1.0f

            val headerModifier = Modifier
                .fillMaxSize()
                .then(
                    Modifier.weight(headerWeight)
                )
            val footerModifier = Modifier
                .fillMaxSize()
                .then(
                    Modifier.weight(footerWeight)
                )

            // Header
            if (!title.isNullOrEmpty()) {
                Row(
                    modifier = headerModifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            } else if (header != null) {
                Row(modifier = headerModifier) {
                    header()
                }
            }

            // Content
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        Modifier.weight(10 - headerWeight - footerWeight)
                    )
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                color = MaterialTheme.colorScheme.background
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .padding(
                                vertical = MaterialTheme.spacing.spaceMedium,
                                horizontal = contentHorizontalPadding
                            )
                    ) {
                        content()
                    }
                    if (footer != null) {
                        Box(
                            modifier = footerModifier
                                .align(Alignment.BottomCenter)
                                .padding(
                                    bottom = with(LocalDensity.current) {
                                        WindowInsets.navigationBars.getBottom(this).toDp()
                                    },
                                ),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            footer()
                        }
                    }
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
