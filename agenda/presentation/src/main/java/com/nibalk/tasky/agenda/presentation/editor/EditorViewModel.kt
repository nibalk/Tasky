package com.nibalk.tasky.agenda.presentation.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import com.nibalk.tasky.agenda.presentation.model.EditorType
import timber.log.Timber

class EditorViewModel(
    editorArgs: EditorArgs,
) : ViewModel() {

    var state by mutableStateOf(EditorState())
        private set

    init {
        Timber.d("[NavIssueLogs] editorArgs.editorText = %s", editorArgs.editorText)
        state = state.copy(
            editorType = EditorType.valueOf(editorArgs.editorType),
            editorText = TextFieldState(initialText = editorArgs.editorText.orEmpty()),
        )
    }

    fun onAction(action: EditorAction) {
        when (action) {
            is EditorAction.OnSaveClicked -> {
                state = state.copy(
                    editorText = TextFieldState(initialText = state.editorText.text.toString()),
                    editorType = state.editorType
                )
            }
            else -> Unit
        }
    }
}
