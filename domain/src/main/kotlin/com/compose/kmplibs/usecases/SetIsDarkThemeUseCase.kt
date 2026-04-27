package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import com.compose.kmplibs.data.utils.catching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

fun interface SetIsDarkThemeUseCase {
    suspend operator fun invoke(isDarkTheme: Boolean)
}

@Factory
class SetIsDarkThemeUseCaseImpl(
    @Provided private val repository: AppPreferencesRepository,
    private val dispatcher: CoroutineDispatcher
) : SetIsDarkThemeUseCase {

    override suspend fun invoke(isDarkTheme: Boolean) {
        catching {
            withContext(dispatcher) {
                repository.setDarkTheme(isDarkTheme)
            }
        }
    }
}
