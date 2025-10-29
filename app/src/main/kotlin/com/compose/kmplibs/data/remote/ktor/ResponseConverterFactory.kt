package com.compose.kmplibs.data.remote.ktor

import com.compose.kmplibs.data.remote.ktor.error.KtorfitException
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.cio.Response
import io.ktor.http.isSuccess
import org.koin.core.annotation.Factory

@Factory
class ResponseConverterFactory : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type != Response::class) {
            return ResponseConverter(typeData, ktorfit)
        }
        return null
    }

    class ResponseConverter(
        private val typeData: TypeData,
        val ktorfit: Ktorfit
    ) : Converter.SuspendResponseConverter<HttpResponse, Any> {
        override suspend fun convert(result: KtorfitResult): Any {
            return when (result) {
                is KtorfitResult.Success -> {
                    if (result.response.status.isSuccess())
                        runCatching {
                            result.response.call.body(typeData.typeInfo)
                        }.getOrElse {
                            throw KtorfitException.Connectivity(
                                data = result.response.bodyAsText(),
                                cause = it
                            )
                        }
                    else throw KtorfitException.Server(
                        response = result.response,
                        bodyText = result.response.bodyAsText()
                    )
                }

                is KtorfitResult.Failure -> throw KtorfitException.Unknown(cause = result.throwable)
            }
        }
    }
}