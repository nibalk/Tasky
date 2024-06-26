package com.nibalk.tasky.auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nibalk.tasky.core.presentation.themes.Inter
import com.nibalk.tasky.core.presentation.themes.TaskyDarkBlue
import com.nibalk.tasky.core.presentation.themes.TaskyGray
import com.nibalk.tasky.core.presentation.themes.TaskyTheme

@Composable
fun AuthClickableText(
    onClick: () -> Unit,
    mainText: String,
    annotatedText: String,
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.SemiBold,
                fontFamily = Inter,
                fontSize = 14.sp,
                color = TaskyGray
            )
        ) {
            append(mainText)
            pushStringAnnotation(
                tag = "clickable_text",
                annotation = stringResource(id = com.nibalk.tasky.auth.presentation.R.string.auth_sign_up).uppercase()
            )
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Inter,
                    fontSize = 14.sp,
                    color = TaskyDarkBlue,
                )
            ) {
                append(annotatedText)
            }
            pop()
        }
    }

    BasicText(
        text = annotatedString,
        modifier = modifier.clickable {
            annotatedString.getStringAnnotations(
                tag = "clickable_text",
                start = 0,
                end = annotatedString.length
            ).firstOrNull()?.let {
                onClick()
            }
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AuthClickableTextPreview() {
    TaskyTheme {
        AuthClickableText(
            onClick = {},
            mainText = "Do you have account ? If not, ",
            annotatedText = "Sign Up",
        )
    }
}
