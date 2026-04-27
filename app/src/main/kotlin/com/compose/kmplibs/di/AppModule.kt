package com.compose.kmplibs.di

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.compose.kmplibs.BuildConfig
import com.compose.kmplibs.data.remote.TMDBApiService
import com.compose.kmplibs.data.remote.createTMDBApiService
import com.compose.kmplibs.data.remote.ktor.ResponseConverterFactory
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
import okio.Path.Companion.toPath
import com.compose.kmplibs.data.di.DataModule
import com.compose.kmplibs.data.di.DispatchersModule
import com.compose.kmplibs.usecases.di.UseCasesModule
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Module(includes = [DispatchersModule::class, DataModule::class, UseCasesModule::class])
@Configuration
@ComponentScan("com.compose.kmplibs")
class AppModule {
    
    @Single
    fun httpClient(): HttpClient {
        return HttpClient {
            defaultRequest {
                headers {
                    HttpHeaders.Accept to "application/json"
                    HttpHeaders.ContentType to "application/json"
                }
                bearerAuth(BuildConfig.ACCESS_TOKEN) // Usa el token de `BuildConfig` quizas se pueda hacer mejor de otra manera
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP Client", message)
                    }
                }
                level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
            }

            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true; isLenient = true })
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
    fun ktorfit(httpClient: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL + "/3/")
            .httpClient(httpClient)
            .converterFactories(ResponseConverterFactory())
            .build()
    }

    @Single
    fun tmdbApiService(@Named("TMDBApi") ktorfit: Ktorfit): TMDBApiService {
        return ktorfit.createTMDBApiService()
    }

    @Single
    fun dataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = listOf(),
            produceFile = { context.filesDir.resolve("kmmlibs.preferences_pb").absolutePath.toPath() }
        )
    }
}
