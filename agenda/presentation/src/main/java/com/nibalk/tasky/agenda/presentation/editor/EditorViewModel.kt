package com.nibalk.tasky.agenda.presentation.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.nibalk.tasky.agenda.presentation.model.AgendaType
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import com.nibalk.tasky.agenda.presentation.model.EditorType

class EditorViewModel(
    private val editorArgs: EditorArgs,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(EditorState())
        private set

    init {
        state = state.copy(
            editorText = if (editorArgs.editorText != state.editorType.name) {
                TextFieldState(initialText = editorArgs.editorText)
            } else TextFieldState(initialText = ""),
            editorType = EditorType.valueOf(editorArgs.editorType),
            agendaType = AgendaType.valueOf(editorArgs.agendaType),
        )
    }

    fun onAction(action: EditorAction) {
        when (action) {
            is EditorAction.OnSaveClicked -> {
                state = state.copy(
                    editorText = TextFieldState(initialText = editorArgs.editorText),
                    editorType = state.editorType
                )
                savedStateHandle[editorArgs.editorType] = action.editorText
            }
            else -> Unit
        }
    }
}
