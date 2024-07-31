package com.nibalk.tasky.agenda.presentation.model

import com.nibalk.tasky.agenda.presentation.R
import com.nibalk.tasky.core.presentation.utils.UiText
import java.time.Duration

enum class ReminderDurationType(
    val label: UiText,
    val duration: Duration,
) {
    TEN_MINUTES(
        UiText.StringResource(R.string.agenda_option_ten_minutes_before),
        Duration.ofMinutes(10),
    ),
    THIRTY_MINUTES(
        UiText.StringResource(R.string.agenda_option_thirty_minutes_before),
        Duration.ofMinutes(30),
    ),
    ONE_HOUR(
        UiText.StringResource(R.string.agenda_option_one_hour_before),
        Duration.ofMinutes(60),
    ),
    SIX_HOURS(
        UiText.StringResource(R.string.agenda_option_six_hours_before),
        Duration.ofMinutes(6 * 60),
    ),
    ONE_DAY(
        UiText.StringResource(R.string.agenda_option_one_day_before),
        Duration.ofMinutes(24 * 60),
    ),
}


