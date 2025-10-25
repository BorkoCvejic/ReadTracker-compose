package com.bcoding.readtracker.di.repository

import com.bcoding.readtracker.book.data.repository.BookRepositoryImpl
import com.bcoding.readtracker.book.domain.repository.BookRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<BookRepository> { BookRepositoryImpl(get()) }
}