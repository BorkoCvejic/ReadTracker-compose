package com.bcoding.readtracker.core.domain

sealed interface Outcome<out D, out E> {
    data class Success<out D>(val data: D) : Outcome<D, Nothing>
    data class Error<out E>(val error: E) : Outcome<Nothing, E>
}

inline fun <T, E> Outcome<T, E>.onSuccess(action: (T) -> Unit): Outcome<T, E> {
    if (this is Outcome.Success) action(data)
    return this
}

inline fun <T, E> Outcome<T, E>.onError(action: (E) -> Unit): Outcome<T, E> {
    if (this is Outcome.Error) action(error)
    return this
}

inline fun <T, E, R> Outcome<T, E>.map(transform: (T) -> R): Outcome<R, E> =
    when (this) {
        is Outcome.Success -> Outcome.Success(transform(data))
        is Outcome.Error -> Outcome.Error(error)
    }

fun <T, E> Outcome<T, E>.asEmptyDataOutcome(): EmptyOutcome<E> {
    return map {}
}

typealias EmptyOutcome<E> = Outcome<Unit, E>