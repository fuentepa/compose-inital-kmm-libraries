package com.compose.kmplibs.data.utils

/**
 * Calls the specified function [action] and returns its encapsulated result into:
 * - [Result.success] if invocation was successful,
 * - [Result.failure] catching any [Throwable] exception that was thrown from the [action] function
 * execution and encapsulating it as [Result.failure].
 *
 * @return [Result] as [T] type.
 */
inline fun <T> catching(action: () -> T): Result<T> = runCatching {
    Result.success(action())
}.getOrElse {
    //Log.e("Error", it)
    Result.failure(it)
}

/**
 * Calls the specified suspend function [action] and returns its encapsulated result into:
 * - [Result.success] if invocation was successful,
 * - [Result.failure] catching any [Throwable] exception that was thrown from the [action] function
 * execution and encapsulating it as [Result.failure].
 *
 * @return [Result] as [T] type.
 */
suspend fun <T> suspendCatching(action: suspend () -> T): Result<T> = runCatching {
    Result.success(action())
}.getOrElse {
    //Log.e("Error", it)
    Result.failure(it)
}