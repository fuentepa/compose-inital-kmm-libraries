package com.compose.kmplibs.di

import android.util.Log
import com.compose.kmplibs.data.remote.StarWarsApiService
import com.compose.kmplibs.data.remote.TMDBApiService
import com.compose.kmplibs.data.remote.UnsuccessResponseConverterFactory
import com.compose.kmplibs.data.repository.StarWarsRepository
import com.compose.kmplibs.data.repository.StarWarsRepositoryImpl
import com.compose.kmplibs.ui.screens.films.FilmsViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.bearerAuth
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module



val appModule = module {
    viewModel { FilmsViewModel(get()) }

    factory<StarWarsRepository> { StarWarsRepositoryImpl(get()) }

    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single { CallConverterFactory() }
    factory { UnsuccessResponseConverterFactory() }
    single(named("StarWarsApi")) {
        ktorfit {
            baseUrl(TMDBApiService.API_URL)

            httpClient(HttpClient {
                defaultRequest {
                    headers {
                        HttpHeaders.Accept to "application/json"
                        HttpHeaders.ContentType to "application/json"
                    }
                    // TODO Token en BuildConfig?
                    //bearerAuth( "el token")  //esto seria el uso basico de token, si se quiere configurar mas cosas se hace con io.ktor:ktor-client-auth plugin
                }

                install(Logging) {
                    //logger = Logger.SIMPLE
                    logger = object: Logger {
                        override fun log(message: String) {
                            Log.d("HTTP Client", message)
                        }
                    }
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(get())
                }
                install(HttpTimeout) {
                    requestTimeoutMillis = 10_000
                    connectTimeoutMillis = 10_000
                    socketTimeoutMillis = 10_000
                }
                /*install(HttpRequestRetry) {
                    maxRetries = 3
                    retryIf { _, response -> !response.status.isSuccess() }
                    retryOnExceptionIf { _, cause -> cause is HttpRequestTimeoutException }
                    delayMillis { 3000L } // retries in 3, 6, 9, etc. seconds
                }*/
            })

            converterFactories(
                UnsuccessResponseConverterFactory()
            )
        }
    }

    single { get<Ktorfit>(named("StarWarsApi")).create<StarWarsApiService>() }
}