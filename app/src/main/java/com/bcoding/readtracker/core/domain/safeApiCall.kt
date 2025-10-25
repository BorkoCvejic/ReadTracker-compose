package com.bcoding.readtracker.core.domain

import android.util.Log
import com.bcoding.readtracker.BuildConfig
import com.bcoding.readtracker.core.data.ApiErrorResponse
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import okio.IOException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Outcome<T, DataError> {
    return try {
        val result = apiCall()
        Outcome.Success(result)
    } catch (e: HttpException) {
        val body = try {
            e.response()?.errorBody()?.string()
        } catch (_: IOException) {
            null
        }
        val apiErrorMessage = body?.let { errorBody ->
            if (BuildConfig.DEBUG) {
                Log.e("safeApiCall", errorBody)
            }
            try {
                val apiError = Json.decodeFromString<ApiErrorResponse>(errorBody)
                apiError.title ?: apiError.detail
            } catch (_: Exception) {
                null
            }
        }

        val code = e.code()
        val message = e.message

        val mappedError = when (code) {
            401 -> DataError.Remote.UNAUTHORIZED
            403 -> DataError.Remote.FORBIDDEN
            404 -> DataError.Remote.NOT_FOUND
            408 -> DataError.Remote.REQUEST_TIMEOUT
            429 -> DataError.Remote.TOO_MANY_REQUESTS
            in 500..599 -> DataError.Remote.SERVER
            else -> DataError.HttpError(code, message)
        }

        val error = apiErrorMessage?.let { errorMessage ->
            DataError.RemoteMessage(errorMessage)
        } ?: mappedError

        Outcome.Error(error)
    } catch (e: IOException) {
        if (BuildConfig.DEBUG) {
            Log.e("safeApiCall", "IO error: ${e.message}")
        }

        val error = when (e) {
            is UnknownHostException,
            is ConnectException,
            is SSLHandshakeException -> DataError.Remote.NO_INTERNET
            is SocketTimeoutException -> DataError.Remote.REQUEST_TIMEOUT
            else -> DataError.Remote.SERVER
        }

        Outcome.Error(error)
    } catch (e: SerializationException) {
        if (BuildConfig.DEBUG) {
            Log.e("safeApiCall", "Serialization error: ${e.message}")
        }
        Outcome.Error(DataError.Remote.SERIALIZATION)
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.e("safeApiCall", "Error: ${e.message}")
        }
        Outcome.Error(DataError.Remote.UNKNOWN)
    }
}
