package com.nibalk.tasky.core.data.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthInfoSerializable(
    val accessTokenExpirationTimestamp: Long,
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String
)
