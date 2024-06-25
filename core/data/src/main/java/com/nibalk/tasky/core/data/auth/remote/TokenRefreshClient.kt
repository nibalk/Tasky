package com.nibalk.tasky.core.data.auth.remote

import com.nibalk.tasky.core.data.BuildConfig
import com.nibalk.tasky.core.data.auth.remote.dto.TokenRequestDto
import com.nibalk.tasky.core.data.auth.remote.dto.TokenResponseDto
import com.nibalk.tasky.core.data.networking.JsonConverterFactoryProvider
import com.nibalk.tasky.core.data.networking.interceptors.ApiKeyInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

object TokenRefreshClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(JsonConverterFactoryProvider.provide())
            .build()
    }

    val api: TokenRefreshApi by lazy {
        retrofit.create(TokenRefreshApi::class.java)
    }
}

interface TokenRefreshApi {

    @POST("/accessToken")
    suspend fun refreshAccessToken(
        @Body body: TokenRequestDto
    ): Response<TokenResponseDto>
}
