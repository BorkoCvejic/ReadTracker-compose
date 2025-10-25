package com.bcoding.readtracker.core.data

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val type: String? = null,
    val title: String? = null,
    val status: Int? = null,
    val detail: String? = null,
    val instance: String? = null
)
