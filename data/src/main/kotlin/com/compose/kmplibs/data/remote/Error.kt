package com.compose.kmplibs.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import kotlinx.io.IOException

typealias Result<T> = Either<Error, T>

sealed class Error {
    class Server(val code: Int) : Error()
    data object Connectivity : Error()
    class Unknown(val message: String) : Error()
}

fun Exception.toError(): Error = when (this) {
    is IOException -> Error.Connectivity
    is KtorfitHttpException -> Error.Server(this.response.status.value) //esto se ajusta a lo que se necesite recoger
    else -> Error.Unknown(message ?: "")
}

inline fun <T> tryCall(action: () -> T): Result<T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left() //lo convierte a Either de error
}