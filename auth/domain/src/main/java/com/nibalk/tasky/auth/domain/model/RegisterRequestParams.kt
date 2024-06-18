package com.nibalk.tasky.auth.domain.model

data class RegisterRequestParams(
    val name: String,
    val email: String,
    val password: String
)
