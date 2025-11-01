package com.bcoding.readtracker.di.viewmodel

import com.bcoding.readtracker.book.presentation.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
}