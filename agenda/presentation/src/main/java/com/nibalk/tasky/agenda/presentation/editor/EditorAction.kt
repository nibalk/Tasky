package com.nibalk.tasky.agenda.presentation.editor

import com.nibalk.tasky.agenda.presentation.model.EditorType

sealed interface EditorAction {
    data object OnBackClicked : EditorAction
    data class OnSaveClicked(
        val editorText: String,
        val editorType: EditorType
    ) : EditorAction
}
