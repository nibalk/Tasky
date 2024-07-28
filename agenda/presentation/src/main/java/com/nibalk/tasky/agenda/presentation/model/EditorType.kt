package com.nibalk.tasky.agenda.presentation.model

import androidx.annotation.StringRes
import com.nibalk.tasky.agenda.presentation.R

enum class EditorType(
    @StringRes val editorTitleResId: Int,
    @StringRes val editorHintResId: Int,
    val isHeaderText: Boolean,
) {
    TITLE(R.string.agenda_editor_title, R.string.agenda_item_enter_title, true),
    DESCRIPTION(R.string.agenda_editor_description, R.string.agenda_item_enter_description, false),
}
