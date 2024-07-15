package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.R
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing


enum class TaskyEditableTextRowType(
    val hasCircularCheck: Boolean,
) {
    TITLE(true),
    DESCRIPTION(false),
}

@Composable
fun TaskyEditableTextRow(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    hint: String = "",
    content: String,
    rowType: TaskyEditableTextRowType,
    isEditable: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isEditable) {
                    Modifier.clickable { onClick() }
                } else Modifier
            )
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(15f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (rowType.hasCircularCheck) {
                TaskyCircularCheckbox(
                    checked = false,
                    contentColor = contentColor,
                    onCheckedChange = {},
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceSmall))
            }
            Text(
                text = content.ifEmpty { hint },
                style = when (rowType) {
                    TaskyEditableTextRowType.TITLE ->
                        MaterialTheme.typography.headlineMedium
                    TaskyEditableTextRowType.DESCRIPTION ->
                        MaterialTheme.typography.labelMedium
                },
                color = if(content.isEmpty()) TaskyDarkGray else contentColor,
            )
        }
        if (isEditable) {
            Icon(
                modifier = Modifier.weight(1f),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.content_description_edit),
                tint = contentColor,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TaskyEditabletextRowTitlePreview() {
    TaskyTheme {
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.TITLE,
            content = "Meeting - This is a very long meeting title",
            isEditable = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TaskyEditableTextRowDescriptionPreview() {
    TaskyTheme {
        TaskyEditableTextRow(
            rowType = TaskyEditableTextRowType.DESCRIPTION,
            content = "This is a very long meeting description, " +
                "since we want to check how the row looks when we have a very lengthy text",
            isEditable = true,
            onClick = {}
        )
    }
}
