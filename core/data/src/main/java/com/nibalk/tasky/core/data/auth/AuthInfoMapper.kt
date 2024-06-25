package com.nibalk.tasky.core.data.auth

import com.nibalk.tasky.core.domain.auth.AuthInfo

fun AuthInfo.toAuthInfoSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        accessToken = accessToken,
        refreshToken = refreshToken,
        fullName = fullName,
        userId = userId
    )
}

fun AuthInfoSerializable.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        accessToken = accessToken,
        refreshToken = refreshToken,
        fullName = fullName,
        userId = userId
    )
}
