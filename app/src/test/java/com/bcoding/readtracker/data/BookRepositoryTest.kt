package com.bcoding.readtracker.data

import com.bcoding.readtracker.book.data.database.BooksDao
import com.bcoding.readtracker.book.data.dto.SearchResponseDto
import com.bcoding.readtracker.book.data.dto.SearchedBookDto
import com.bcoding.readtracker.book.data.mappers.toBook
import com.bcoding.readtracker.book.data.repository.BookRepositoryImpl
import com.bcoding.readtracker.book.domain.model.SearchResult
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
    private val booksDao = mockk<BooksDao>()
    private val repository by inject<BookRepository>()

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single { api }
                    single { booksDao }
                    single<BookRepository> { BookRepositoryImpl(get(), get()) }
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
                    bookId = "1",
                    title = "Harry Potter and the Sorcerer's Stone",
                    authorNames = listOf("JK Rowling"),
                    languages = listOf("eng"),
                    numEditions = 3,
                    ratingAverage = 4.45,
                    ratingCount = 100,
                    numPagesMedian = 300
                )
            ),
            resultsSize = 1
        )

        coEvery {
            api.searchBooks(
                query = "Harry Potter",
                offset = 0,
                limit = 10
            )
        } returns mockedDto

        val result = repository.searchBooks(
            query = "Harry Potter",
            offset = 0,
            limit = 10
        )

        val expectedBooks = SearchResult(
            books = mockedDto.results.map { searchedBookDto -> searchedBookDto.toBook() },
            hasMore = mockedDto.resultsSize > 10
        )

        assertTrue(result is Outcome.Success)
        assertEquals(expectedBooks, (result as Outcome.Success).data)
    }

    @Test
    fun `searchBooks returns error when network fails`() = runTest {
        coEvery {
            api.searchBooks(
                query = any(),
                offset = any(),
                limit = any()
            )
        } throws UnknownHostException()

        val result = repository.searchBooks(
            query = "test",
            offset = 0,
            limit = 10
        )

        assertTrue(result is Outcome.Error)
        assertEquals(DataError.Remote.NO_INTERNET, (result as Outcome.Error).error)
    }

    @Test
    fun `searchBooks calculates hasMore correctly`() = runTest {
        val mockedDto = SearchResponseDto(
            results = List(10) { index ->
                SearchedBookDto(
                    bookId = index.toString(),
                    title = "Book $index",
                    authorNames = listOf("Author"),
                    languages = listOf("eng"),
                    numEditions = 1,
                    ratingAverage = 4.0,
                    ratingCount = 10,
                    numPagesMedian = 200
                )
            },
            resultsSize = 100
        )

        coEvery {
            api.searchBooks(
                query = "test",
                offset = 0,
                limit = 10,
            )
        } returns mockedDto

        val result = repository.searchBooks(
            query = "test",
            offset = 0,
            limit = 10
        )

        assertTrue(result is Outcome.Success)
        val searchResult = (result as Outcome.Success).data

        // hasMore should be true as 10 out of 100 results are in response
        assertTrue(searchResult.hasMore)
        assertEquals(10, searchResult.books.size)
    }
}
