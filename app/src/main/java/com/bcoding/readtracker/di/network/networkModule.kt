package com.bcoding.readtracker.di.network

import com.bcoding.readtracker.BuildConfig
import com.bcoding.readtracker.core.data.Api
import com.bcoding.readtracker.di.network.NetworkConfig.addPrettyLoggingInterceptor
import com.bcoding.readtracker.di.network.NetworkConfig.addRetryInterceptor
import com.bcoding.readtracker.di.network.NetworkConfig.applyTimeouts
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .applyTimeouts()
            .addPrettyLoggingInterceptor()
            .addRetryInterceptor()
            .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single<Api> { get<Retrofit>().create(Api::class.java) }
}
