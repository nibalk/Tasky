package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyHeader(
    modifier: Modifier = Modifier,
    content: (@Composable RowScope.() -> Unit)? = null,
) {
    Column {
        if (content != null) {
            Row(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = MaterialTheme.spacing.spaceMedium,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        } else {
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceLarge))
        }
    }
}




