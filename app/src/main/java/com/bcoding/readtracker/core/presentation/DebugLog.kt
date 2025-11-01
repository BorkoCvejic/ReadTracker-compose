package com.bcoding.readtracker.core.presentation

import com.bcoding.readtracker.BuildConfig

object DebugLog {
    fun log(tag: String, message: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            println("[$tag] $message")
            throwable?.printStackTrace()
        }
    }
}
