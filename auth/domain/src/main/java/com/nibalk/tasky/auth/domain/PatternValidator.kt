package com.nibalk.tasky.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}
