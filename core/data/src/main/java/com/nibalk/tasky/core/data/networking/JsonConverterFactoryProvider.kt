@file:OptIn(ExperimentalSerializationApi::class)

package com.nibalk.tasky.core.data.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter

object JsonConverterFactoryProvider {
    fun provide(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint =true
        }
        return json.asConverterFactory(contentType)
    }
}
