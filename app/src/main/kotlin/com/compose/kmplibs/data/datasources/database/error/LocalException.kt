package com.compose.kmplibs.data.datasources.database.error

import com.compose.kmplibs.data.model.error.BaseErrorType
import com.compose.kmplibs.data.model.error.BaseException
import com.compose.kmplibs.data.remote.ktor.error.localStorageEmptyException

sealed class LocalException(
    type: BaseErrorType = BaseErrorType.Unknown,
    override val cause: Throwable? = null
) : BaseException(type, cause) {

    data class LocalStorage(
        val bodyText: String,
        val type: BaseErrorType = localStorageEmptyException("$bodyText"),
        override val cause: Throwable? = null
    ) : LocalException(type, cause)

}