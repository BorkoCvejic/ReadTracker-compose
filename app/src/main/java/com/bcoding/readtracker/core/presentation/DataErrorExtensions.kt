package com.bcoding.readtracker.core.presentation

import com.bcoding.readtracker.R
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.presentation.UiText.*

fun DataError.toUiText(): UiText = when (this) {
    // Remote errors
    DataError.Remote.NO_INTERNET -> StringResourceId(R.string.error_no_internet)
    DataError.Remote.SERIALIZATION -> StringResourceId(R.string.error_serialization)
    DataError.Remote.UNAUTHORIZED -> StringResourceId(R.string.error_unauthorized)
    DataError.Remote.FORBIDDEN -> StringResourceId(R.string.error_forbidden)
    DataError.Remote.NOT_FOUND -> StringResourceId(R.string.error_not_found)
    DataError.Remote.REQUEST_TIMEOUT -> StringResourceId(R.string.error_request_timeout)
    DataError.Remote.TOO_MANY_REQUESTS -> StringResourceId(R.string.error_too_many_requests)
    DataError.Remote.SERVER -> StringResourceId(R.string.error_server)
    DataError.Remote.UNKNOWN -> StringResourceId(R.string.error_unknown)

    // Local errors
    DataError.Local.DISK_FULL -> StringResourceId(R.string.error_disk_full)
    DataError.Local.UNKNOWN -> StringResourceId(R.string.error_unknown)

    // HTTP or custom message
    is DataError.HttpError -> message?.let { DynamicString(message) }
            ?: run { StringResourceId(R.string.error_unknown) }

    // Remote message directly from backend
    is DataError.RemoteMessage -> DynamicString(message)
}
