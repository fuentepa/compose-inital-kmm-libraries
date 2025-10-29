package com.compose.kmplibs.data.remote.ktor.error

import com.compose.kmplibs.data.model.error.BaseErrorType


data class UnknownKtorfitException(val message: String?) : BaseErrorType(message)
data class InvalidServerResponseCode(val message: String?) : BaseErrorType(message)
data class InvalidConnectivityResponseCode(val message: String?) : BaseErrorType(message)
data class localStorageEmptyException(val message: String?) : BaseErrorType(message)