package com.bcoding.readtracker.book.presentation.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.core.domain.onError
import com.bcoding.readtracker.core.domain.onSuccess
import com.bcoding.readtracker.core.presentation.toUiText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailsViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    private val _state = MutableStateFlow(BookDetailsState())
    val state = _state
        .onStart {
            fetchBookDescription()
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
            }
            BookDetailsScreenActions.OnBackClick -> {
                viewModelScope.launch {
                    _events.emit(BookDetailsUiEvents.NavigateBack)
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
                .getDescription(currentBook.id)
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
}
