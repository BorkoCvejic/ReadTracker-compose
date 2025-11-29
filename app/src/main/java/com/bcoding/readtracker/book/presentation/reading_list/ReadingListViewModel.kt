package com.bcoding.readtracker.book.presentation.reading_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.book.presentation.shared.actions.BookSharedActions
import com.bcoding.readtracker.book.presentation.reading_list.ReadingListScreenUiEvents.*
import com.bcoding.readtracker.book.presentation.reading_list.ReadingListState.Companion.PAGE_SIZE
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.presentation.navigation.Routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReadingListViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val readingListId = savedStateHandle.toRoute<Routes.ReadingList>().readingListId
    private val _state = MutableStateFlow(ReadingListState())
    val state = _state
        .onStart {
            loadBooksFromReadingList(isInitial = true)
            observeTotalBooksCount()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _events = MutableSharedFlow<ReadingListScreenUiEvents>()
    val events = _events.asSharedFlow()

    private var loadBooksJob: Job? = null

    fun onAction(action: UiActions) {
        when (action) {
            is BookSharedActions.OnBookClick -> {
                viewModelScope.launch {
                    _events.emit(NavigateToBookDetails(action.book))
                }
            }
            is ReadingListScreenActions.OnLoadMore -> {
                loadBooksFromReadingList(isInitial = false)
            }
        }
    }

    private fun loadBooksFromReadingList(isInitial: Boolean) {
        val currentState = _state.value

        if (!isInitial && (currentState.isLoadingMore || !currentState.hasMore)) return

        if (isInitial) {
            _state.update { currentState ->
                currentState.copy(
                    savedBooks = emptyList(),
                    currentPage = 0,
                    isLoadingMore = false
                )
            }
        } else {
            _state.update { currentState ->
                currentState.copy(
                    isLoadingMore = true
                )
            }
        }

        val offset = if (isInitial) 0 else currentState.currentPage * PAGE_SIZE

        loadBooksJob?.cancel()
        loadBooksJob = bookRepository
            .getBooksFromReadingListPaginated(
                readingListId = readingListId,
                offset = offset,
                limit = PAGE_SIZE
            )
            .onEach { savedBooks ->
                _state.update { currentState ->
                    val newBooks = if (isInitial) {
                        savedBooks
                    } else {
                        currentState.savedBooks + savedBooks
                    }

                    currentState.copy (
                        savedBooks = newBooks,
                        currentPage = if (isInitial) 1 else currentState.currentPage + 1,
                        isLoadingMore = false
                    )
                }
                updateCanLoadMore()
            }
            .launchIn(viewModelScope)
    }

    private fun updateCanLoadMore() {
        _state.update { currentState ->
            val loadedCount = currentState.savedBooks.size
            val totalCount = currentState.totalBooksCount
            val newCanLoadMore = loadedCount < totalCount

            currentState.copy(
                hasMore = newCanLoadMore
            )
        }
    }

    private fun observeTotalBooksCount() {
        bookRepository
            .getBookCountInReadingList(readingListId = readingListId)
            .distinctUntilChanged()
            .onEach { totalBooksCount ->
                val currentState = _state.value
                val loadedCount = currentState.savedBooks.size

                if (totalBooksCount < loadedCount) {
                    _state.update { currentState ->
                        currentState.copy(
                            totalBooksCount = totalBooksCount,
                            currentPage = 0,
                            savedBooks = emptyList(),
                            isLoadingMore = false
                        )
                    }
                    loadBooksFromReadingList(isInitial = true)
                } else {
                    _state.update { currentState ->
                        currentState.copy(
                            totalBooksCount = totalBooksCount
                        )
                    }
                    updateCanLoadMore()
                }
            }
            .launchIn(viewModelScope)
    }
}
