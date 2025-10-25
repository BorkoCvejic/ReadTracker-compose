package com.bcoding.readtracker.di.network

import android.util.Log
import com.bcoding.readtracker.BuildConfig
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object NetworkConfig {
    const val CONNECT_TIMEOUT = 10L
    const val READ_TIMEOUT = 25L
    const val WRITE_TIMEOUT = 20L
    const val CALL_TIMEOUT = 40L

    fun Builder.applyTimeouts(): Builder {
       return connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
           .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
           .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
           .callTimeout(CALL_TIMEOUT, TimeUnit.SECONDS)
    }

    fun Builder.addPrettyLoggingInterceptor(): Builder {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor.Logger { message ->
                try {
                    val prettyJson = when {
                        message.trim().startsWith("{") ->
                            JSONObject(message).toString(2)
                        message.trim().startsWith("[") ->
                            JSONArray(message).toString(2)
                        else -> message
                    }
                    Log.d("OkHttp", prettyJson)
                } catch (e: Exception) {
                    Log.e("OkHttp", "Pretty print failed: ${e.message}", e)
                    Log.d("OkHttp", "Raw message: $message")
                }
            }
            HttpLoggingInterceptor(logger).apply { level = HttpLoggingInterceptor.Level.BODY }
        } else {
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.NONE }
        }

        return addInterceptor(loggingInterceptor)
    }

    fun Builder.addRetryInterceptor(): Builder {
        return addInterceptor(RetryInterceptor())
    }
}
