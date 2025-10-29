package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

interface GetDeviceIdUseCase {
    operator fun invoke(): Flow<Long>
}

@Factory
class GetDeviceIdUseCaseImpl(
    private val repository: AppPreferencesRepository
) : GetDeviceIdUseCase  {

    override fun invoke(): Flow<Long> = repository.getDeviceId()
}