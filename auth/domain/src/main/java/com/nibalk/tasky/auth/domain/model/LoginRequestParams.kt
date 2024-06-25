package com.nibalk.tasky.auth.domain.model

data class LoginRequestParams(
    val email: String,
    val password: String
)
