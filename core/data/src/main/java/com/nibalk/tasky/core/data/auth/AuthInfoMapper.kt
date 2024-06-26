package com.nibalk.tasky.core.data.auth

import com.nibalk.tasky.core.domain.auth.AuthInfo

fun AuthInfo.toAuthInfoDto(): AuthInfoDto {
    return AuthInfoDto(
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        accessToken = accessToken,
        refreshToken = refreshToken,
        fullName = fullName,
        userId = userId
    )
}

fun AuthInfoDto.toAuthInfo(): AuthInfo {
    return AuthInfo(
        accessTokenExpirationTimestamp = accessTokenExpirationTimestamp,
        accessToken = accessToken,
        refreshToken = refreshToken,
        fullName = fullName,
        userId = userId
    )
}
