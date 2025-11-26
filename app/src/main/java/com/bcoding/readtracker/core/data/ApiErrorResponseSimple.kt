package com.bcoding.readtracker.core.data

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponseSimple(
    val error: String
)
