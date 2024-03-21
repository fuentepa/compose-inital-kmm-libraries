package com.compose.kmplibs.di

import android.util.Log
import com.compose.kmplibs.data.remote.StarWarsApiService
import com.compose.kmplibs.data.remote.UnsuccessResponseConverterFactory
import com.compose.kmplibs.data.repository.StarWarsRepository
import com.compose.kmplibs.data.repository.StarWarsRepositoryImpl
import com.compose.kmplibs.ui.screens.films.FilmsViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.builtin.CallConverterFactory
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
import io.ktor.client.plugins.logging.*
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
            baseUrl(StarWarsApiService.API_URL)
            httpClient(HttpClient {
                install(Logging) {
                    logger = object: Logger {
                        override fun log(message: String) {
                            Log.d("HTTP Client", message)
                        }
                    }
                    level = LogLevel.BODY
                }
                install(ContentNegotiation) {
                    json(get())
                }
                defaultRequest {
                    headers {
                        HttpHeaders.Accept to "application/json"
                        HttpHeaders.ContentType to "application/json"
                    }
                }
            })
            converterFactories(
                UnsuccessResponseConverterFactory()
            )
        }
    }

    single { get<Ktorfit>(named("StarWarsApi")).create<StarWarsApiService>() }
}