package com.bcoding.readtracker.domain

import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome
import com.bcoding.readtracker.core.domain.safeApiCall
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.UnknownHostException
import retrofit2.HttpException
import retrofit2.Response

class SafeApiCallTest {
    @Test
    fun `safeApiCall should return Outcome_Success when api call is successful`() = runTest {
        val apiCall = "Hello World"
        val result = safeApiCall {
            apiCall
        }

        assertTrue(result is Outcome.Success)
        assertEquals(apiCall, (result as Outcome.Success).data)
    }

    @Test
    fun `safeApiCall should return Outcome_Error with NO_INTERNET for UnknownHostException`() = runTest {
        val result = safeApiCall<String> {
            throw UnknownHostException()
        }

        assertTrue(result is Outcome.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as Outcome.Error).error)
    }

    @Test
    fun `safeApiCall should return Outcome_Error with SERVER for HttpException`() = runTest {
        val responseBody = "".toResponseBody("application/json".toMediaTypeOrNull())
        val response = Response.error<String>(500, responseBody)
        val httpException = HttpException(response)

        val result = safeApiCall<String> {
            throw httpException
        }

        assertTrue(result is Outcome.Error)
        assertEquals(DataError.Remote.SERVER, (result as Outcome.Error).error)
    }

    @Test
    fun `safeApiCall should return Outcome_Error with UNKNOWN for other exceptions`() = runTest {
        val result = safeApiCall<String> {
            throw IllegalStateException("Something went wrong")
        }

        assertTrue(result is Outcome.Error)
        assertEquals(DataError.Remote.UNKNOWN, (result as Outcome.Error).error)
    }
}
