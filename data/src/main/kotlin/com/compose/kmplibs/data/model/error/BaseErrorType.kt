package com.compose.kmplibs.data.model.error

open class BaseErrorType(
    val error: String? = null
) {
    data object Unknown : BaseErrorType()
}
