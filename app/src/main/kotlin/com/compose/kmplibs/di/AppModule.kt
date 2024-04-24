package com.compose.kmplibs.di

import android.util.Log
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.data.remote.TMDBApiService
import com.compose.kmplibs.data.remote.UnsuccessResponseConverterFactory
import com.compose.kmplibs.data.repository.MoviesRepository
import com.compose.kmplibs.data.repository.MoviesRepositoryImpl
import com.compose.kmplibs.ui.screens.movieDetails.MovieDetailViewModel
import com.compose.kmplibs.ui.screens.movies.MoviesViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val appModule = module {
    // TODO
    viewModel { MoviesViewModel(get()) }
    viewModel { parameters -> MovieDetailViewModel(movieId = parameters.get(), get()) }

    single<MoviesRepository> { MoviesRepositoryImpl(get()) }

    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single { CallConverterFactory() }
    factory { UnsuccessResponseConverterFactory() }
    single(named("TMDBApi")) {
        ktorfit {
            baseUrl(BuildConfig.TMDB_BASE_URL)

            httpClient(HttpClient {
                defaultRequest {
                    headers {
                        HttpHeaders.Accept to "application/json"
                        HttpHeaders.ContentType to "application/json"
                    }
                    // TODO Token en BuildConfig?
                    bearerAuth( BuildConfig.ACCESS_TOKEN)  //esto seria el uso basico de token, si se quiere configurar mas cosas se hace con io.ktor:ktor-client-auth plugin
                }

                install(Logging) {
                    // logger = Logger.SIMPLE
                    logger = object : Logger {
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

    single { get<Ktorfit>(named("TMDBApi")).create<TMDBApiService>() }
}