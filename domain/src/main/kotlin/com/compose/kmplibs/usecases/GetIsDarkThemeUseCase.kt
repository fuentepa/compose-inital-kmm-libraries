package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

interface GetIsDarkThemeUseCase {
    operator fun invoke(): Flow<Boolean>
}

@Factory
class GetIsDarkThemeUseCaseImpl(
    private val repository: AppPreferencesRepository
) : GetIsDarkThemeUseCase  {

    override fun invoke(): Flow<Boolean> = repository.isDarkTheme()
}