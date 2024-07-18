package com.nibalk.tasky.agenda.presentation.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nibalk.tasky.agenda.presentation.model.EditorArgs
import com.nibalk.tasky.agenda.presentation.model.EditorType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber

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
        )
//        viewModelScope.launch {
//            snapshotFlow { state.editorText.text }
//                .distinctUntilChanged()
//                .collectLatest { text ->
//                    state = state.copy(
//                        editorText = TextFieldState(initialText = text.toString())
//                    )
//                    Timber.d("[EditorLogs] VM snapshotFlow = %s", state.editorText.text.toString())
//                    savedStateHandle[editorArgs.editorType] = state.editorText.text.toString()
//                }
//        }

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
