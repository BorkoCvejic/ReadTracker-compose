package com.bcoding.readtracker.core.domain

sealed interface DataError {
    enum class Remote: DataError {
        NO_INTERNET,
        SERIALIZATION,
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        SERVER,
        UNKNOWN
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }

    data class HttpError(
        val code: Int,
        val message: String? = null
    ): DataError

    data class RemoteMessage(
        val message: String
    ): DataError
}
