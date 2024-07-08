package com.nibalk.tasky.agenda.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nibalk.tasky.agenda.presentation.R

enum class AgendaItemActionType(
    @StringRes val menuNameResId: Int,
    @DrawableRes val menuIconResId: Int,
) {
    OPEN(R.string.agenda_option_open, android.R.drawable.ic_menu_view),
    EDIT(R.string.agenda_option_edit, android.R.drawable.ic_menu_edit),
    DELETE(R.string.agenda_option_delete, android.R.drawable.ic_menu_delete),
}


