package com.bcoding.readtracker.ui

import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.book.presentation.search.SearchScreenActions
import com.bcoding.readtracker.book.presentation.search.SearchScreenUiEvents
import com.bcoding.readtracker.book.presentation.search.SearchViewModel
import com.bcoding.readtracker.book.presentation.shared.actions.BookSharedActions
import com.bcoding.readtracker.core.domain.DataError
import com.bcoding.readtracker.core.domain.Outcome
import com.bcoding.readtracker.core.presentation.toUiText
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest : KoinTest {

    private val testDispatcher = StandardTestDispatcher()

    private val bookRepository = mockk<BookRepository>()

    private val searchViewModel: SearchViewModel by inject()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(
                module {
                    single { bookRepository }
                    viewModelOf(::SearchViewModel)
                }
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `searchBooks updates state to success`() = runTest {
        val query = "Harry Potter"
        val mockedBooks = listOf(
            Book(
                bookId = "1",
                title = "Harry Potter and the Sorcerer's Stone",
                imageUrl = "https://example.com/image.jpg",
                authors = listOf("J.K. Rowling"),
                languages = listOf("eng"),
                numEditions = 3,
                description = "Description",
                firstPublishedYear = "2002",
                ratingAverage = 4.45,
                ratingCount = 100,
                numPages = 300
            )
        )

        coEvery { bookRepository.searchBooks(query) } returns Outcome.Success(mockedBooks)

        val stateJob = launch {
            searchViewModel.state.collect {}
        }

        searchViewModel.onAction(SearchScreenActions.OnSearchQueryChange(query))

        advanceTimeBy(500L)
        advanceUntilIdle()

        val state = searchViewModel.state.value
        Assert.assertFalse(state.isLoading)
        Assert.assertNull(state.error)
        Assert.assertEquals(mockedBooks, state.books)

        stateJob.cancel()
    }

    @Test
    fun `searchBooks updates state to error`() = runTest {
        val query = "Harry Potter"
        val error = DataError.Remote.NO_INTERNET

        coEvery { bookRepository.searchBooks(query) } returns Outcome.Error(error)

        val searchJob = launch {
            searchViewModel.state.collect {}
        }

        searchViewModel.onAction(SearchScreenActions.OnSearchQueryChange(query))
        advanceTimeBy(500L)
        advanceUntilIdle()

        val state = searchViewModel.state.value
        Assert.assertFalse(state.isLoading)
        Assert.assertEquals(error.toUiText(), state.error)
        Assert.assertTrue(state.books.isEmpty())

        searchJob.cancel()
    }

    @Test
    fun `onBookClick emits NavigateToBookDetails event`() = runTest {
        val mockedBook = Book(
            bookId = "42",
            title = "Harry Potter and the Sorcerer's Stone",
            imageUrl = "https://example.com/image.jpg",
            authors = listOf("JK Rowling"),
            languages = listOf("eng"),
            numEditions = 3,
            description = "Description",
            firstPublishedYear = "2002",
            ratingAverage = 4.45,
            ratingCount = 100,
            numPages = 300
        )
        val expectedEvent = SearchScreenUiEvents.NavigateToBookDetails(book = mockedBook)

        val events = mutableListOf<SearchScreenUiEvents>()

        val job = launch {
            searchViewModel.events.collect { events.add(it) }
        }

        searchViewModel.onAction(BookSharedActions.OnBookClick(book = mockedBook))
        advanceUntilIdle()

        Assert.assertEquals(expectedEvent, events.first())

        job.cancel()
    }
}