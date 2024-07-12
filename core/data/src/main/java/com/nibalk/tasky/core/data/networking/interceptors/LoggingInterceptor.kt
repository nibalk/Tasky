package com.nibalk.tasky.core.data.networking.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber


class LoggingInterceptor : Interceptor {
    private val logger = HttpLoggingInterceptor { message ->
        Timber.d("[LoggingInterceptorLogs] - ", message)
    }.apply {
        level= HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return logger.intercept(chain)
    }
}
