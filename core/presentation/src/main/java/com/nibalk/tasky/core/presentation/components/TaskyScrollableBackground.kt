package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyScrollableBackground(
    title: String? = null,
    contentHorizontalPadding: Dp = MaterialTheme.spacing.spaceMedium,
    header: (@Composable RowScope.() -> Unit)? = null,
    footer: (@Composable BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    TaskyBackground (
        title =  title,
        header = header,
        footer = footer,
        contentHorizontalPadding = contentHorizontalPadding,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            content()
        }
    }
}

