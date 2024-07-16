package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
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
import com.nibalk.tasky.core.presentation.themes.TaskyBrownLight
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun TaskyEditableNotificationRow2(
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    content: String,
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
            Box(
                modifier = Modifier
                    .background(
                        TaskyBrownLight,
                        RoundedCornerShape(MaterialTheme.spacing.spaceSmall)
                    )
                    .padding(MaterialTheme.spacing.spaceSmall)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = stringResource(R.string.content_description_edit),
                    tint = contentColor.copy(
                        alpha = 0.5f
                    )
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceSmall))
            Text(
                text = content,
                style = MaterialTheme.typography.labelMedium,
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

