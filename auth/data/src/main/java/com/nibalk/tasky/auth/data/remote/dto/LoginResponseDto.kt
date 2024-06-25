package com.nibalk.tasky.auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    @SerialName("accessTokenExpirationTimestamp")
    val accessTokenExpiry: Long,
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("fullName")
    val name: String,
    @SerialName("userId")
    val userId: String
)
