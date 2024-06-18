package com.nibalk.tasky.auth.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    @SerialName("fullName")
    val userName: String,
    @SerialName("email")
    val userEmail: String,
    @SerialName("password")
    val userPassword: String
)
