package com.compose.kmplibs.di

import android.util.Log
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.data.remote.TMDBApiService
import com.compose.kmplibs.data.remote.UnsuccessResponseConverterFactory
import com.compose.kmplibs.data.remote.createTMDBApiService
import de.jensklingenberg.ktorfit.Ktorfit
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
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.compose.kmplibs")
class AppModule {

    @Single
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    @Single
    fun provideHttpClient(json: Json): HttpClient {
        return HttpClient {
            defaultRequest {
                headers {
                    HttpHeaders.Accept to "application/json"
                    HttpHeaders.ContentType to "application/json"
                }
                bearerAuth(BuildConfig.ACCESS_TOKEN) // Usa el token de `BuildConfig`
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP Client", message)
                    }
                }
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 10_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 10_000
            }
        }
    }

    @Single
    @Named("TMDBApi")
    fun provideKtorfit(httpClient: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL + "/3/")
            .httpClient(httpClient)
            .converterFactories(UnsuccessResponseConverterFactory())
            .build()
    }

    @Single
    fun provideTMDBApiService(@Named("TMDBApi") ktorfit: Ktorfit): TMDBApiService {
        return ktorfit.createTMDBApiService()
    }

}