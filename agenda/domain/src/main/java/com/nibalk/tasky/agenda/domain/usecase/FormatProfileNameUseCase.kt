package com.nibalk.tasky.agenda.domain.usecase

class FormatProfileNameUseCase() {
    operator fun invoke(fullName: String): String {
        val names = fullName.trim().split(" ")
        return when (names.size) {
            0 -> ""
            1 -> names[0].take(2).uppercase()
            else -> "${names[0][0]}${names[names.size - 1][0]}".uppercase()
        }
    }
}
