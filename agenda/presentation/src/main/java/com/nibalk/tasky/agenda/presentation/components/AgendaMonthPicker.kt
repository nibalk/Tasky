package com.nibalk.tasky.agenda.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.core.presentation.themes.spacing
import java.time.LocalDate

@Composable
fun AgendaMonthPicker(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate,
    onMonthClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable { onMonthClick() }
            .padding(MaterialTheme.spacing.spaceExtraSmall)
    ) {
        Text(
            text = selectedDate.month.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.spaceSmall))
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            tint = MaterialTheme.colorScheme.onSecondary,
            contentDescription = stringResource(R.string.content_description_change_month)
        )
    }
}

@Preview
@Composable
fun AgendaMonthPickerPreview() {
    AgendaMonthPicker(
        selectedDate = LocalDate.now(),
        onMonthClick = {}
    )
}
