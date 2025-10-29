package com.compose.kmplibs.data.model.error

open class BaseException(
    type: BaseErrorType = BaseErrorType.Unknown,
    override val cause: Throwable? = null
) : Exception(type.error)