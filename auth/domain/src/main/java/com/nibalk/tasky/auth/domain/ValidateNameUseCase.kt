package com.nibalk.tasky.auth.domain

import com.nibalk.tasky.auth.domain.utils.AuthDataValidator

class ValidateNameUseCase() {
    operator fun invoke(name: String): Boolean {
        return (name.length in AuthDataValidator.NAME_MIN_LENGTH..AuthDataValidator.NAME_MAX_LENGTH)
    }
}
