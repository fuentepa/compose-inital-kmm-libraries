package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

interface GetDeviceIdUseCase {
    operator fun invoke(): Flow<Long>
}

@Factory
class GetDeviceIdUseCaseImpl(
    @Provided private val repository: AppPreferencesRepository
) : GetDeviceIdUseCase  {

    override fun invoke(): Flow<Long> = repository.getDeviceId()
}
