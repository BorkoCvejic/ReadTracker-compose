package com.bcoding.readtracker.data

import com.bcoding.readtracker.book.data.dto.SearchResponseDto
import com.bcoding.readtracker.book.data.dto.SearchedBookDto
import com.bcoding.readtracker.book.data.mappers.toBook
import com.bcoding.readtracker.book.data.repository.BookRepositoryImpl
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.core.data.Api
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import java.net.UnknownHostException

class BookRepositoryTest : KoinTest {

    private val api = mockk<Api>()
    private val repository by inject<BookRepository>()

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single { api }
                    single<BookRepository> { BookRepositoryImpl(get()) }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `searchBooks returns success when api call succeeds`() = runTest {
        val mockedDto = SearchResponseDto(
            results = listOf(
                SearchedBookDto(
                    id = "1",
                    title = "Harry Potter and the Sorcerer's Stone",
                    authorNames = listOf("JK Rowling"),
                    languages = listOf("eng"),
                    numEditions = 3,
                    ratingAverage = 4.45,
                    ratingCount = 100,
                    numPagesMedian = 300
                )
            )
        )

        coEvery { api.searchBooks("Harry Potter") } returns mockedDto

        val result = repository.searchBooks("Harry Potter")

        val expectedBooks = mockedDto.results.map { it.toBook() }

        assertTrue(result is Outcome.Success)
        assertEquals(expectedBooks, (result as Outcome.Success).data)
    }

    @Test
    fun `searchBooks returns error when network fails`() = runTest {
        coEvery { api.searchBooks(any()) } throws UnknownHostException()

        val result = repository.searchBooks("test")

        assertTrue(result is Outcome.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as Outcome.Error).error)
    }
}
