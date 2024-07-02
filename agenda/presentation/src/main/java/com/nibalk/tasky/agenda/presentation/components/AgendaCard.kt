package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.agenda.domain.model.AgendaItem
import com.nibalk.tasky.core.presentation.components.TaskyCircularCheckbox
import com.nibalk.tasky.core.presentation.themes.TaskyBrownLight
import com.nibalk.tasky.core.presentation.themes.TaskyDarkGray
import com.nibalk.tasky.core.presentation.themes.TaskyGreen
import com.nibalk.tasky.core.presentation.themes.TaskyLightGreen
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.TaskyWhite
import com.nibalk.tasky.core.presentation.themes.spacing
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AgendaCard(
    modifier: Modifier = Modifier,
    item: AgendaItem,
    datePattern : String = "dd MMM, HH:mm",
    onOptionsClick: () -> Unit,
    onItemClick: () -> Unit,
    onIsDone: (Boolean) -> Unit,
) {
    val dateFormatter = DateTimeFormatter.ofPattern(datePattern)
    val itemDate = if (item is AgendaItem.Event) {
        remember(item.startAt, item.endAt) {
            buildString {
                append(item.startAt.format(dateFormatter))
                append(" - ")
                append(item.endAt.format(dateFormatter))
            }
        }
    } else {
        remember(item.startAt) {
            item.startAt.format(dateFormatter)
        }
    }

    val contentColor = if (item is AgendaItem.Task) TaskyWhite else TaskyDarkGray
    val backgroundColor: Color = when (item) {
        is AgendaItem.Task -> TaskyGreen
        is AgendaItem.Event -> TaskyLightGreen
        is AgendaItem.Reminder -> TaskyBrownLight
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = LocalConfiguration.current.screenWidthDp.dp)
            .heightIn(min = MaterialTheme.spacing.spaceDoubleExtraLarge)
            .clip(RoundedCornerShape(MaterialTheme.spacing.spaceMedium))
            .clickable {
                onItemClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        // Title Row
        AgendaCardHeader(
            title = item.title,
            isDone = if (item is AgendaItem.Task) item.isDone else false,
            contentColor = contentColor,
            onOptionsClick = onOptionsClick,
            onItemClick = onItemClick,
            onIsDone = onIsDone
        )
        // Description
        Text(
            text = item.description,
            style = MaterialTheme.typography.displaySmall,
            color = contentColor,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(
                    start = MaterialTheme.spacing.spaceExtraLarge,
                    end = MaterialTheme.spacing.spaceExtraLarge,
                    bottom = MaterialTheme.spacing.spaceMedium,
                )
        )
        // Date
        Text(
            text = itemDate,
            style = MaterialTheme.typography.displaySmall,
            color = contentColor,
            modifier = Modifier
                .align(Alignment.End)
                .padding(
                    end = MaterialTheme.spacing.spaceMedium,
                    bottom = MaterialTheme.spacing.spaceMedium,
                )
        )
    }
}

@Composable
private fun AgendaCardHeader(
    title: String,
    isDone: Boolean,
    contentColor: Color,
    onOptionsClick: () -> Unit,
    onItemClick: () -> Unit,
    onIsDone: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.spaceSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onItemClick() }
                .weight(9f)
        ) {
            // Title Row - Checkbox
            TaskyCircularCheckbox(
                checked = isDone,
                contentColor = contentColor,
                onCheckedChange = onIsDone,
            )
            // Title Row - Title
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceMedium))
            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = contentColor,
                textDecoration = if (isDone) TextDecoration.LineThrough else null
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceMedium))
        }
        // Title Row - More Options
        IconButton(
            onClick = onOptionsClick,
            modifier = Modifier
                .weight(1f),
        ) {
            Icon(
                modifier = Modifier.rotate(90.0f),
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "options",
                tint = contentColor
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AgendaCardTaskPreview() {
    TaskyTheme {
        AgendaCard(
            item = AgendaItem.Task(
                id = "",
                title = "Tasky Project - Finishing Complete Auth Feature",
                description = "Completing the Auth feature including the Login and Register flows",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                isDone = true
            ),
            onOptionsClick = {},
            onItemClick = {},
            onIsDone = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AgendaCardEventPreview() {
    TaskyTheme {
        AgendaCard(
            item = AgendaItem.Event(
                id = "",
                title = "Tasky Event Bi-Weekly call",
                description = "Bi-weekly call 02",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
                endAt = LocalDateTime.now(),
                hostId = "",
                isHost = false,
            ),
            onOptionsClick = {},
            onItemClick = {},
            onIsDone = {}
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun AgendaCardReminderPreview() {
    TaskyTheme {
        AgendaCard(
            item = AgendaItem.Reminder(
                id = "",
                title = "Tasky Reminder",
                description = "Reminder to implement offline first feature",
                startAt = LocalDateTime.now(),
                remindAt = LocalDateTime.now(),
            ),
            onOptionsClick = {},
            onItemClick = {},
            onIsDone = {}
        )
    }
}
