package com.compose.kmplibs.data.remote.ktor.error

import com.compose.kmplibs.data.model.error.BaseErrorType
import com.compose.kmplibs.data.model.error.BaseException
import io.ktor.client.statement.HttpResponse
import kotlin.jvm.Transient

sealed class KtorfitException(
    type: BaseErrorType = BaseErrorType.Unknown,
    override val cause: Throwable? = null
) : BaseException(type, cause) {

    /**
     * Represents an invalid server response such a 4xx, 5xxx, ...
     */
    data class Server(
        @Transient val response: HttpResponse,
        val bodyText: String,
        val type: BaseErrorType = InvalidServerResponseCode("HTTP ${response.status.value}: $bodyText"),
        override val cause: Throwable? = null
    ) : KtorfitException(type, cause)

    /**
     * Represents an invalid conversion of service response, e.g. expects ObjectA and
     * receives ObjectB.
     */
    data class Connectivity(
        val data: String? = null,
        val type: BaseErrorType = InvalidConnectivityResponseCode(data),
        override val cause: Throwable? = null
    ) : KtorfitException(type, cause)

    data class Unknown(
        val data: String? = null,
        val type: BaseErrorType = UnknownKtorfitException(data),
        override val cause: Throwable? = null
    ) : KtorfitException(type, cause)
}