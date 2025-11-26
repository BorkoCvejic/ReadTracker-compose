package com.bcoding.readtracker.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.bcoding.readtracker.book.domain.model.Book
import com.bcoding.readtracker.book.domain.model.ReadingListOverview
import com.bcoding.readtracker.book.presentation.shared.view_models.SelectedBookViewModel
import com.bcoding.readtracker.book.presentation.book_details.BookDetailsScreenActions
import com.bcoding.readtracker.book.presentation.book_details.BookDetailsScreenRoot
import com.bcoding.readtracker.book.presentation.book_details.BookDetailsViewModel
import com.bcoding.readtracker.book.presentation.library.LibraryScreenRoot
import com.bcoding.readtracker.book.presentation.library.LibraryViewModel
import com.bcoding.readtracker.book.presentation.reading_list.ReadingListScreenRoot
import com.bcoding.readtracker.book.presentation.reading_list.ReadingListViewModel
import com.bcoding.readtracker.book.presentation.search.SearchScreenRoot
import com.bcoding.readtracker.book.presentation.search.SearchViewModel
import com.bcoding.readtracker.core.presentation.navigation.Routes.*
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.searchNavGraph(
    modifier: Modifier,
    showDetails: (Book) -> Unit
) {
    navigation<SearchGraph>(
        startDestination = Search
    ) {
        composable<Search> {
            val searchViewModel = koinViewModel<SearchViewModel>()
            SearchScreenRoot(
                modifier = modifier,
                searchViewModel = searchViewModel,
                showDetails = { book -> showDetails(book) }
            )
        }
    }
}

fun NavGraphBuilder.progressTrackerGraph(modifier: Modifier) {
    navigation<ProgressTrackerGraph>(
        startDestination = ProgressTracker
    ) {
        composable<ProgressTracker> {
            Text(modifier = modifier, text = "progress tracker")
        }
    }
}

fun NavGraphBuilder.libraryGraph(
    modifier: Modifier,
    showReadingList: (ReadingListOverview) -> Unit,
    navigateUp: () -> Unit,
    showDetails: (Book) -> Unit
) {
    navigation<LibraryGraph>(
        startDestination = Library
    ) {
        composable<Library> {
            val libraryViewModel = koinViewModel<LibraryViewModel>()
            LibraryScreenRoot(
                modifier = modifier,
                libraryViewModel = libraryViewModel,
                showReadingList = { readingList -> showReadingList(readingList) }
            )
        }
        composable<ReadingList> { backStackEntry ->
            val readingListName = backStackEntry.toRoute<ReadingList>().readingListName
            val readingListViewModel = koinViewModel<ReadingListViewModel>()

            ReadingListScreenRoot(
                readingListViewModel = readingListViewModel,
                readingListName = readingListName,
                navigateUp = { navigateUp() },
                showDetails = { book -> showDetails(book)}
            )
        }
    }
}

fun NavGraphBuilder.sharedGraph(
    selectedBookViewModel: SelectedBookViewModel,
    navigateUp: () -> Unit
) {
    composable<BookDetails> {
        val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
        val bookDetailsViewModel = koinViewModel<BookDetailsViewModel>()

        LaunchedEffect(selectedBook) {
            selectedBook?.let { book ->
                bookDetailsViewModel.onAction(
                    BookDetailsScreenActions.OnSelectedBookChange(book)
                )
            }
        }

        BookDetailsScreenRoot(
            bookDetailsViewModel = bookDetailsViewModel,
            navigateUp = { navigateUp() }
        )
    }
}