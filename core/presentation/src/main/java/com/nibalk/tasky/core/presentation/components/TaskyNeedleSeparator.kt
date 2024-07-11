package com.nibalk.tasky.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.core.presentation.themes.TaskyBlack
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun TaskyNeedleSeparator(
    modifier: Modifier = Modifier,
    color: Color = TaskyBlack,
    circleRadius: Dp = 5.dp,
    strokeHeight: Dp = 4.dp,
) {
    Row(
        modifier = modifier
            .height(circleRadius)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(circleRadius)
                .background(color, CircleShape)
                .align(Alignment.CenterVertically),
        )
        HorizontalDivider(
            modifier = Modifier
                .height(strokeHeight)
                .padding(top = 1.dp)
                .align(Alignment.CenterVertically),
            color = color
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun TaskyNeedleSeparatorPreview() {
    TaskyTheme {
        TaskyNeedleSeparator()

    }
}
