package com.bcoding.readtracker.book.presentation.reading_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.book.presentation.shared.actions.BookSharedActions
import com.bcoding.readtracker.book.presentation.reading_list.ReadingListScreenUiEvents.*
import com.bcoding.readtracker.book.presentation.shared.actions.UiActions
import com.bcoding.readtracker.core.presentation.navigation.Routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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
            observeSavedBooks()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _events = MutableSharedFlow<ReadingListScreenUiEvents>()
    val events = _events.asSharedFlow()

    private var observeSavedBooksJob: Job? = null

    fun onAction(action: UiActions) {
        when (action) {
            is BookSharedActions.OnBookClick -> {
                viewModelScope.launch {
                    _events.emit(NavigateToBookDetails(action.book))
                }
            }
        }
    }

    private fun observeSavedBooks() {
        observeSavedBooksJob?.cancel()
        observeSavedBooksJob = bookRepository
            .getBooksFromReadingList(readingListId)
            .onEach { savedBooks ->
                _state.update { currentState ->
                    currentState.copy (
                        savedBooks = savedBooks
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
