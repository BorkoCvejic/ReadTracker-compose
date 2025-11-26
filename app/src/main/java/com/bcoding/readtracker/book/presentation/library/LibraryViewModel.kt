package com.bcoding.readtracker.book.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.book.presentation.library.LibraryScreenUiEvents.*
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

class LibraryViewModel(
    private val bookRepository: BookRepository
): ViewModel() {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state
        .onStart {
            observeReadingLists()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _events = MutableSharedFlow<LibraryScreenUiEvents>()
    val events = _events.asSharedFlow()

    fun onAction(action: LibraryScreenActions) {
        when (action) {
            is LibraryScreenActions.OnReadingListClick -> {
                viewModelScope.launch {
                    _events.emit(NavigateToBookDetails(readingListOverview = action.readingList))
                }
            }
            is LibraryScreenActions.OnDeleteReadingListClick -> {
                viewModelScope.launch {
                    bookRepository.deleteReadingList(readingListId = action.readingListId)
                }
            }
            is LibraryScreenActions.OnRenameReadingListClick -> {
                viewModelScope.launch {
                    bookRepository.renameReadingList(
                        readingListId = action.readingListId,
                        newReadingListName = action.newReadingListName
                    )
                }
            }
            is LibraryScreenActions.OnCreateNewReadingListDialogClick -> {
                viewModelScope.launch {
                    bookRepository.createReadingList(action.readingListName)
                }
            }
            LibraryScreenActions.OnCreateNewReadingListFABClick -> {
                viewModelScope.launch {
                    _events.emit(ShowCreateNewReadingListDialog)
                }
            }
        }
    }

    private fun observeReadingLists() {
        bookRepository
            .getAllReadingLists()
            .onEach { allReadingLists ->
                _state.update { currentState ->
                    currentState.copy(
                        readingLists = allReadingLists
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
