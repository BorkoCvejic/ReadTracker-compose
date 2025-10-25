package com.bcoding.readtracker.book.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcoding.readtracker.book.domain.repository.BookRepository
import com.bcoding.readtracker.core.domain.onError
import com.bcoding.readtracker.core.domain.onSuccess
import com.bcoding.readtracker.core.presentation.toUiText
import kotlinx.coroutines.launch

class HomeViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    init {
        searchBooks("Harry Potter")
    }

    fun searchBooks(query: String) = viewModelScope.launch {
        bookRepository.searchBooks(query)
            .onSuccess { searchResult ->
                println(searchResult[0].title)
            }
            .onError { error ->
                println(error.toUiText())
            }
    }
}
