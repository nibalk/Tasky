package com.nibalk.tasky.agenda.presentation.editor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import com.nibalk.tasky.agenda.presentation.model.EditorType
import com.nibalk.tasky.core.presentation.components.TaskyScrollableBackground
import com.nibalk.tasky.core.presentation.components.TaskyTextField
import com.nibalk.tasky.core.presentation.themes.spacing
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditorScreenRoot(
    onBackClicked: () -> Unit,
    onSaveClicked: (String, EditorType)-> Unit,
    editorArgs: EditorArgs,
    viewModel: EditorViewModel = koinViewModel { parametersOf(editorArgs) },
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    EditorScreen(
        state = viewModel.state,
        onAction = { action ->
            when(action) {
                is EditorAction.OnBackClicked -> {
                    keyboardController?.hide()
                    onBackClicked()
                }
                is EditorAction.OnSaveClicked -> {
                    keyboardController?.hide()
                    onSaveClicked(action.editorText, action.editorType)
                }
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun EditorScreen(
    state: EditorState,
    onAction: (EditorAction) -> Unit
) {
    TaskyScrollableBackground {
        EditorScreenHeader(
            headerTitle = stringResource(
                id = R.string.agenda_item_edit, stringResource(state.editorType.editorTitleResId)
            ).uppercase(),
            onGoBack = {
                onAction(EditorAction.OnBackClicked)
            },
            onSaveDetail = {
                onAction(EditorAction.OnSaveClicked(
                    editorText = state.editorText.text.toString(),
                    editorType = state.editorType,
                ))
            },
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.spaceMedium,
                )
        )
        TaskyTextField(
            state = state.editorText,
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.spaceMedium),
            hint = stringResource(id = state.editorType.editorHintResId),
            textStyle = if (state.editorType.isHeaderText) {
                MaterialTheme.typography.headlineMedium
            } else {
                MaterialTheme.typography.labelMedium
            },
            lines = TextFieldLineLimits.MultiLine(
                minHeightInLines = 3,
                maxHeightInLines = 10,
            )
        )
    }
}

@Composable
private fun EditorScreenHeader(
    modifier: Modifier = Modifier,
    headerTitle: String,
    onGoBack: () -> Unit,
    onSaveDetail: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onGoBack() }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = stringResource(R.string.content_description_back)
            )
        }
        Text(
            text = headerTitle.uppercase(),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
        Text(
            modifier = Modifier
                .clickable { onSaveDetail() },
            text = stringResource(id = R.string.agenda_item_save),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
