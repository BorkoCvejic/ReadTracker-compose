package com.bcoding.readtracker.di.viewmodel

import com.bcoding.readtracker.book.presentation.shared.view_models.SelectedBookViewModel
import com.bcoding.readtracker.book.presentation.book_details.BookDetailsViewModel
import com.bcoding.readtracker.book.presentation.library.LibraryViewModel
import com.bcoding.readtracker.book.presentation.reading_list.ReadingListViewModel
import com.bcoding.readtracker.book.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { BookDetailsViewModel(get()) }
    viewModel { SelectedBookViewModel() }
    viewModel { LibraryViewModel(get()) }
    viewModel { ReadingListViewModel(get(), get()) }
}
