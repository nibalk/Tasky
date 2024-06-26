package com.nibalk.tasky.core.domain.auth

import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result

interface TokenRefresher {
    suspend fun refreshAccessToken(
        authInfo: AuthInfo
    ): Result<AuthInfo, DataError.Network>
}
