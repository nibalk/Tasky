package com.nibalk.tasky.core.data.auth

import com.nibalk.tasky.core.data.auth.remote.TokenRefreshClient
import com.nibalk.tasky.core.data.auth.remote.dto.TokenRequestDto
import com.nibalk.tasky.core.data.networking.safeCall
import com.nibalk.tasky.core.domain.auth.AuthInfo
import com.nibalk.tasky.core.domain.auth.TokenRefresher
import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import com.nibalk.tasky.core.domain.util.map

class AutoTokenRefresher(
    private val tokenRefreshClient: TokenRefreshClient
): TokenRefresher {

    override suspend fun refreshAccessToken(
        authInfo: AuthInfo
    ): Result<AuthInfo, DataError.Network> {
        val response = safeCall {
            tokenRefreshClient.api.refreshAccessToken(
                body = TokenRequestDto(
                    refreshToken = authInfo.refreshToken,
                    userId = authInfo.userId,
                )
            )
        }
        return response.map { tokenResponseDto ->
            if (tokenResponseDto != null) {
                AuthInfo(
                    accessTokenExpirationTimestamp = tokenResponseDto.expirationTimestamp,
                    accessToken = tokenResponseDto.accessToken,
                    refreshToken = authInfo.refreshToken,
                    fullName = authInfo.fullName,
                    userId = authInfo.userId,
                )
            } else {
                authInfo
            }
        }
    }
}
