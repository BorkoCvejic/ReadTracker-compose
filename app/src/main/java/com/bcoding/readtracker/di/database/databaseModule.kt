package com.bcoding.readtracker.di.database

import androidx.room.Room
import com.bcoding.readtracker.book.data.database.BooksDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = BooksDatabase::class.java,
            name = BooksDatabase.DB_NAME
        ).build()
    }

    single {
        get<BooksDatabase>().booksDao
    }
}
