package com.nibalk.tasky.core.data.networking.interceptors

import com.nibalk.tasky.core.domain.auth.SessionStorage
import com.nibalk.tasky.core.domain.auth.TokenRefresher
import com.nibalk.tasky.core.domain.util.onError
import com.nibalk.tasky.core.domain.util.onSuccess
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class TokenInterceptor(
    private val sessionStorage: SessionStorage,
    private val tokenRefresher: TokenRefresher
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authInfo = runBlocking {
            return@runBlocking sessionStorage.get()
        }

        val requestBuilder = chain.request().newBuilder()

        if (authInfo != null) {
            Timber.d("[TokenInterceptorLogs] - accessToken = ${authInfo.accessToken}")
            var validAccessToken = authInfo.accessToken

            if (authInfo.accessTokenExpirationTimestamp < System.currentTimeMillis()) {
                val newAccessToken = runBlocking {
                    return@runBlocking tokenRefresher.refreshAccessToken(authInfo)
                }

                newAccessToken
                    .onSuccess { newToken ->
                        runBlocking {
                            sessionStorage.set(newToken)
                            validAccessToken = newToken.accessToken
                        }
                    }
                    .onError {
                        validAccessToken = authInfo.accessToken
                    }
            }

            requestBuilder
                .addHeader("Authorization", "Bearer $validAccessToken")
        }
        return chain.proceed(requestBuilder.build())
    }
}
