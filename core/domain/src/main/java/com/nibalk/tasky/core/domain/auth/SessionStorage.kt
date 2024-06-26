package com.nibalk.tasky.core.domain.auth

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}
