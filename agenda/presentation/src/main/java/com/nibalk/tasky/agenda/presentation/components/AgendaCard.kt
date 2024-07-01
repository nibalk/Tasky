package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import com.nibalk.tasky.agenda.domain.AgendaItem
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
    onOptionsClick: () -> Unit,
    onItemClick: () -> Unit,
    onIsDone: (Boolean) -> Unit,
) {
    val itemDate by remember {
        derivedStateOf {
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm")
            if (item is AgendaItem.Event) {
                buildString {
                    append(item.eventFromDateTime.format(dateFormatter))
                    append(" - ")
                    append(item.eventToDateTime.format(dateFormatter))
                }
            } else {
                item.time.format(dateFormatter)
            }
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
                    checked = item.isDone,
                    contentColor = contentColor,
                    onCheckedChange = onIsDone,
                )
                // Title Row - Title
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceMedium))
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.displayMedium,
                    color = contentColor,
                    textDecoration = if (item.isDone) {
                        TextDecoration.LineThrough
                    } else null
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

@Preview
@Composable
fun AgendaCardTaskPreview() {
    TaskyTheme {
        Column(
            Modifier
                .background(TaskyWhite)
                .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            AgendaCard(
                item = AgendaItem.Task(
                    taskId = "",
                    taskTitle = "Tasky Project - Finishing Complete Auth Feature",
                    taskDescription = "Completing the Auth feature including the Login and Register flows",
                    taskDateTime = LocalDateTime.now(),
                    taskRemindAt = LocalDateTime.now(),
                    taskIsDone = true
                ),
                onOptionsClick = {},
                onItemClick = {},
                onIsDone = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
            AgendaCard(
                item = AgendaItem.Task(
                    taskId = "",
                    taskTitle = "Tasky Project",
                    taskDescription = "Agenda feature",
                    taskDateTime = LocalDateTime.now(),
                    taskRemindAt = LocalDateTime.now(),
                    taskIsDone = false
                ),
                onOptionsClick = {},
                onItemClick = {},
                onIsDone = {}
            )
        }
    }
}

@Preview
@Composable
fun AgendaCardEventPreview() {
    TaskyTheme {
        Column(
            Modifier
                .background(TaskyWhite)
                .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            AgendaCard(
                item = AgendaItem.Event(
                    eventId = "",
                    eventTitle = "Tasky Event Bi-Weekly call",
                    eventDescription = "Bi-weekly call 02",
                    eventRemindAt = LocalDateTime.now(),
                    eventFromDateTime = LocalDateTime.now(),
                    eventToDateTime = LocalDateTime.now(),
                    eventAttendees = emptyList(),
                    eventPhotos = emptyList(),
                    eventHostId = "",
                    eventIsHost = false,
                    eventIsDone = false
                ),
                onOptionsClick = {},
                onItemClick = {},
                onIsDone = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
            AgendaCard(
                item = AgendaItem.Event(
                    eventId = "",
                    eventTitle = "Tasky Event",
                    eventDescription = "Bi-weekly Tasky call 03",
                    eventRemindAt = LocalDateTime.now(),
                    eventFromDateTime = LocalDateTime.now(),
                    eventToDateTime = LocalDateTime.now(),
                    eventAttendees = emptyList(),
                    eventPhotos = emptyList(),
                    eventHostId = "",
                    eventIsHost = false,
                    eventIsDone = true
                ),
                onOptionsClick = {},
                onItemClick = {},
                onIsDone = {}
            )
        }
    }
}

@Preview
@Composable
fun AgendaCardRemindPreview() {
    TaskyTheme {
        Column(
            Modifier
                .background(TaskyWhite)
                .padding(MaterialTheme.spacing.spaceMedium)
        ) {
            AgendaCard(
                item = AgendaItem.Reminder(
                    reminderId = "",
                    reminderTitle = "Tasky Reminder",
                    reminderDescription = "Reminder to implement offline first feature",
                    reminderRemindAt = LocalDateTime.now(),
                    reminderDateTime = LocalDateTime.now(),
                    reminderIsDone = true
                ),
                onOptionsClick = {},
                onItemClick = {},
                onIsDone = {}
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.spaceMedium))
            AgendaCard(
                item = AgendaItem.Reminder(
                    reminderId = "",
                    reminderTitle = "Remind",
                    reminderDescription = "",
                    reminderRemindAt = LocalDateTime.now(),
                    reminderDateTime = LocalDateTime.now(),
                    reminderIsDone = false
                ),
                onOptionsClick = {},
                onItemClick = {},
                onIsDone = {}
            )
        }
    }
}
