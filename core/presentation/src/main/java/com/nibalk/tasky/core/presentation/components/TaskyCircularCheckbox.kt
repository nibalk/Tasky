package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyWhite
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyCircularCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    contentColor: Color,
    onCheckedChange: (Boolean) -> Unit,
    size: Dp = MaterialTheme.spacing.spaceLarge
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .size(size)
            .border(
                width = 1.dp,
                color = contentColor,
                shape = CircleShape
            )
            .clickable {
                onCheckedChange(!checked)
            },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "Checked",
                tint = contentColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF279F70)
@Composable
fun TaskyCircularCheckboxOnPreview() {
    TaskyCircularCheckbox(
        checked = true,
        contentColor = TaskyWhite,
        onCheckedChange = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF279F70)
@Composable
fun TaskyCircularCheckboxfPreview() {
    TaskyCircularCheckbox(
        checked = false,
        contentColor = TaskyWhite,
        onCheckedChange = {}
    )
}



