package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyLightBlue
import com.nibalk.tasky.core.presentation.themes.TaskyTheme
import com.nibalk.tasky.core.presentation.themes.spacing

@Composable
fun AgendaFooter(
    modifier: Modifier = Modifier,
    contentColor: Color = TaskyGray,
    content: String,
    onButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        HorizontalDivider(color = TaskyLightBlue)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = MaterialTheme.spacing.spaceMedium,
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = content.uppercase(),
                style = MaterialTheme.typography.displayMedium,
                color = contentColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    onButtonClicked()
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AgendaFooterPreview() {
    TaskyTheme {
        AgendaFooter(
            content = "Delete Task",
            onButtonClicked = {}
        )
    }
}
