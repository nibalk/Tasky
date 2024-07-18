package com.nibalk.tasky.agenda.presentation.editor

import androidx.compose.foundation.text.input.TextFieldState
import com.nibalk.tasky.agenda.presentation.model.EditorType

data class EditorState(
    val editorText: TextFieldState = TextFieldState(),
    val editorHint: String = "",
    val editorType: EditorType = EditorType.TITLE,
)


