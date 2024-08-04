package com.nibalk.tasky.core.data.networking

import com.nibalk.tasky.core.domain.util.DataError
import com.nibalk.tasky.core.domain.util.Result
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException
import retrofit2.Response
import timber.log.Timber
import java.nio.channels.UnresolvedAddressException


inline fun <reified T> safeCall(execute: () -> Response<T>): Result<T?, DataError.Network> {
    val response = try {
        execute()
    } catch(e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch(e: Exception) {
        Timber.d("[SafeCall-Exception] SAFE-CALL | Exception (%s)", e)
        if(e is CancellationException) throw e
        e.printStackTrace()
        Timber.d("[SafeCall-Exception] SAFE-CALL | Exception Stacktrace (%s)", e.printStackTrace())
        return Result.Error(DataError.Network.UNKNOWN)
    }

    return responseToResult(response)
}

inline fun <reified T> responseToResult(response: Response<T>): Result<T?, DataError.Network> {
    Timber.d("[SafeCall-Exception] SAFE-CALL | responseToResult (%s)", response)
    return when(response.code()) {
        in 200..299 -> Result.Success(response.body())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}
