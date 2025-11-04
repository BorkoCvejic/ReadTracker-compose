package com.bcoding.readtracker.book.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcoding.readtracker.book.domain.repository.BookRepository
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

    fun onAction(action: SearchScreenActions) {
        when (action) {
            is SearchScreenActions.OnSearchQueryChange -> {
                _state.update { currentState ->
                    currentState.copy(searchQuery = action.query)
                }
            }
            is SearchScreenActions.OnBookClick -> {
                viewModelScope.launch {
                    _events.emit(SearchScreenUiEvents.NavigateToBookDetails(action.book))
                }
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
                if (query.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchBooks(query)
                } else if (query.isEmpty()) {
                    _state.update { currentState ->
                        currentState.copy(
                            books = emptyList(),
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        bookRepository.searchBooks(query)
            .onSuccess { searchResult ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = null,
                        books = searchResult
                    )
                }
            }
            .onError { error ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = error.toUiText(),
                        books = emptyList()
                    )
                }
            }
    }
}
