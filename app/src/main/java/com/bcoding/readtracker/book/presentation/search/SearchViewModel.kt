package com.bcoding.readtracker.book.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.book.presentation.shared.actions.BookSharedActions
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.domain.onError
import com.bcoding.readtracker.core.domain.onSuccess
import com.bcoding.readtracker.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    companion object {
        const val PAGE_SIZE = 10
    }

    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .onStart {
            observeSearchQuery()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var searchJob: Job? = null

    private val _events = MutableSharedFlow<SearchScreenUiEvents>()
    val events = _events.asSharedFlow()

    fun onAction(action: UiActions) {
        when (action) {
            is SearchScreenActions.OnSearchQueryChange -> {
                _state.update { currentState ->
                    currentState.copy(searchQuery = action.query)
                }
            }
            is BookSharedActions.OnBookClick -> {
                viewModelScope.launch {
                    _events.emit(SearchScreenUiEvents.NavigateToBookDetails(book = action.book))
                }
            }
            is SearchScreenActions.OnLoadMore -> {
                val currentState = _state.value
                if (currentState.isLoading || currentState.isLoadingMore || !currentState.canLoadMore) return

                searchBooks(
                    query = currentState.searchQuery,
                    isNewSearch = false
                )
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { currentState -> currentState.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                if (query.length >= 3) {
                    searchJob?.cancel()
                    searchJob = searchBooks(
                        query = query,
                        isNewSearch = true
                    )
                } else if (query.isEmpty()) {
                    _state.update { currentState ->
                        currentState.copy(
                            books = emptyList(),
                            currentPage = 0
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String, isNewSearch: Boolean) = viewModelScope.launch {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }

        if (isNewSearch) {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true,
                    isLoadingMore = false,
                    currentPage = 0,
                    books = emptyList()
                )
            }
        } else {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    isLoadingMore = true
                )
            }
        }

        val page = if (isNewSearch) 0 else _state.value.currentPage
        val offset = page * PAGE_SIZE

        bookRepository.searchBooks(query, offset, PAGE_SIZE)
            .onSuccess { searchResult ->
                _state.update { currentState ->
                    val newBooks = if (isNewSearch) {
                        searchResult.books
                    } else {
                        currentState.books + searchResult.books
                    }

                    currentState.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = null,
                        books = newBooks,
                        canLoadMore = searchResult.hasMore,
                        currentPage = page + 1
                    )
                }
            }
            .onError { error ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        error = error.toUiText()
                    )
                }
            }
    }
}
