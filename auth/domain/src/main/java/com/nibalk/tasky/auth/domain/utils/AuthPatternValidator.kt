package com.nibalk.tasky.auth.domain.utils

interface AuthPatternValidator {
    fun matches(value: String): Boolean
}
