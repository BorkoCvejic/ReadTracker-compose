package com.bcoding.readtracker.book.presentation.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.book.presentation.book_details.BookDetailsUiEvents.*
import com.bcoding.readtracker.core.domain.onError
import com.bcoding.readtracker.core.domain.onSuccess
import com.bcoding.readtracker.core.presentation.toUiText
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

class BookDetailsViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    private val _state = MutableStateFlow(BookDetailsState())
    private var readingListsJob: Job? = null

    val state = _state
        .onStart {
            fetchBookDescription()
            observeAvailableReadingLists()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _events = MutableSharedFlow<BookDetailsUiEvents>()
    val events = _events.asSharedFlow()

    fun onAction(action: BookDetailsScreenActions) {
        when (action) {
            is BookDetailsScreenActions.OnSelectedBookChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        book = action.book
                    )
                }
                observeReadingListsForBook(bookId = action.book.bookId)
            }
            BookDetailsScreenActions.OnBackClick -> {
                viewModelScope.launch {
                    _events.emit(NavigateBack)
                }
            }
            BookDetailsScreenActions.OnAddToReadingListClick -> {
                viewModelScope.launch {
                    state.value.book?.let { currentBook ->
                        _events.emit(ShowAddToListDialog(currentBook))
                    }
                }
            }
            is BookDetailsScreenActions.OnSaveBookToReadingListsClick -> {
                viewModelScope.launch {
                    bookRepository.setBookReadingLists(
                        book = action.book,
                        readingListIds = action.selectedReadingLists
                    )
                    observeReadingListsForBook(bookId = action.book.bookId)
                }
            }
            is BookDetailsScreenActions.OnCreateNewReadingListClick -> {
                viewModelScope.launch {
                    bookRepository.createReadingList(action.readingListName)
                }
            }
        }
    }

    private fun fetchBookDescription() = viewModelScope.launch {
        _state.update { currentState ->
            currentState.copy(
                isLoadingDescription = true
            )
        }
        _state.value.book?.let { currentBook ->
            bookRepository
                .getDescription(currentBook.bookId)
                .onSuccess { description ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoadingDescription = false,
                            error = null,
                            book = currentState.book?.copy(
                                description = description.description
                            )
                        )
                    }
                }
                .onError { error ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoadingDescription = false,
                            error = error.toUiText()
                        )
                    }
                }
        }
    }

    private fun observeAvailableReadingLists() {
        bookRepository
            .getAllReadingLists()
            .onEach { availableReadingLists ->
                _state.update { currentState ->
                    currentState.copy(
                        availableReadingLists = availableReadingLists
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeReadingListsForBook(bookId: String) {
        readingListsJob?.cancel()
        readingListsJob = bookRepository
            .getReadingListsForBook(bookId)
            .onEach { selectedListIds ->
                _state.update { currentState ->
                    currentState.copy(
                        selectedListIds = selectedListIds
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
