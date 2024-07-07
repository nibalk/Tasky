package com.nibalk.tasky.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing


data class TaskyDropDownMenuItem(
    @DrawableRes val leadingIcon: Int? = null,
    val displayText: String,
    val onClick: () -> Unit,
)

@Composable
fun TaskyDropDownMenu(
    isMenuShown: Boolean,
    onDismissRequest: () -> Unit,
    menuItems: List<TaskyDropDownMenuItem>,
) {
    DropdownMenu(
        expanded = isMenuShown,
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.primary,
        border = BorderStroke(width = 1.dp, color = TaskyLightBlue),
        shape = RoundedCornerShape(MaterialTheme.spacing.spaceSmall),
        shadowElevation = 8.dp,
        tonalElevation = 8.dp,
    ) {
        menuItems.forEach { item ->
            DropdownMenuItem(
                leadingIcon = item.leadingIcon?.let { iconId ->
                    {
                        Icon(
                            painter = painterResource(id = iconId),
                            contentDescription = item.displayText
                        )
                    }
                },
                text = {
                    Text(
                        text = item.displayText,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                onClick = {
                    item.onClick()
                },
            )
            HorizontalDivider(color = TaskyLightBlue)
        }
    }
}

@Preview
@Composable
fun TaskyDropDownMenuPreview() {

    val sampleItems = listOf(
        TaskyDropDownMenuItem(
            displayText = "Open",
            leadingIcon = android.R.drawable.ic_menu_view,
            onClick = {}
        ),
        TaskyDropDownMenuItem(
            displayText = "Edit",
            leadingIcon = android.R.drawable.ic_menu_edit,
            onClick = {}
        ),
        TaskyDropDownMenuItem(
            displayText = "Delete",
            leadingIcon = android.R.drawable.ic_menu_delete,
            onClick = {}
        )
    )

    TaskyTheme {
        TaskyDropDownMenu(
            isMenuShown = true,
            menuItems = sampleItems,
            onDismissRequest = {},
        )
    }
}
