# ReadTracker

Android application built with Jetpack Compose for searching books and tracking reading progress over time. The app allows users to search for books, create custom reading lists, and manage their book collection.

## Features

- Search books using Open Library API
- View book details
- Manage custom reading lists

## Architecture & Design Patterns

- Clean Architecture: separation into data, domain, presentation
- MVVM in presentation layer
- Repository pattern for data access abstraction

## Tech Stack

- Room - Local database
- Koin - Dependency injection
- Coroutines + Flow - Asynchronous operations and reactive streams
- Coil - Image loading
- Retrofit + OkHttp - HTTP client with interceptors
- Kotlinx Serialization - JSON serialization
- Type-safe Compose Navigation - Navigation with sealed classes
- Testing - JUnit, MockK, Compose Testing

## API

The app uses the [Open Library API](https://openlibrary.org/developers/api) for book search and details.

## TODO - Future Features

- Backup System: Implement export/import functionality for the database
- Work Manager Integration: Add daily automatic database backup using Work Manager
- Reading Progress: Add statistics about reading progress
- Read Books Section: Add a dedicated section for completed/read books
