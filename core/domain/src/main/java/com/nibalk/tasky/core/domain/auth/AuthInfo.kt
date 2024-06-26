package com.nibalk.tasky.core.domain.auth

data class AuthInfo(
    val accessTokenExpirationTimestamp: Long,
    val accessToken: String,
    val refreshToken: String,
    val fullName: String,
    val userId: String
)
