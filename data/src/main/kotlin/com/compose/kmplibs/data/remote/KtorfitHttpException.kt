package com.compose.kmplibs.data.remote

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import org.koin.core.annotation.Factory

class KtorfitHttpException(  //for success responses with http error code inside
    @Transient val response: HttpResponse,
    val bodyText: String,
) : RuntimeException() {
    override val message: String
        get() = "HTTP ${response.status.value}: $bodyText"
}

@Factory
class UnsuccessResponseConverterFactory : Converter.Factory {

    class UnsuccessResponseSuspendConverter(
        val typeData: TypeData,
        val ktorfit: Ktorfit
    ) : Converter.SuspendResponseConverter<HttpResponse, Any> {
        override suspend fun convert(result: KtorfitResult): Any {
            return when(result) {
                is KtorfitResult.Success -> {
                    if (result.response.status.isSuccess())
                        result.response.call.body(typeData.typeInfo)
                    else {
                        throw KtorfitHttpException(result.response, result.response.bodyAsText())
                    }
                }
                is KtorfitResult.Failure -> throw result.throwable
            }

        }
    }

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != Response::class) {
            return UnsuccessResponseSuspendConverter(typeData, ktorfit)
        }
        return null
    }
}