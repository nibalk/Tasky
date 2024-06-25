package com.nibalk.tasky.core.data.auth.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponseDto(
    val accessToken: String,
    val expirationTimestamp: Long
)
