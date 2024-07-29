package com.nibalk.tasky.agenda.presentation.detail

import com.nibalk.tasky.core.presentation.utils.UiText

sealed interface DetailEvent {
    data object DetailSaveSuccess: DetailEvent
    data class DetailSaveError(val error: UiText): DetailEvent
}
