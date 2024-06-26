package com.nibalk.tasky.core.data.auth.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenRequestDto(
    val refreshToken: String,
    val userId: String
)
