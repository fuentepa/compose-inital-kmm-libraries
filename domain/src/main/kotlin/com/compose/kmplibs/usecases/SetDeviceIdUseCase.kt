package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.datasources.features.preferences.AppPreferencesRepository
import com.compose.kmplibs.data.utils.catching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

fun interface SetDeviceIdUseCase {
    suspend operator fun invoke(deviceId: Long)
}

@Factory
class SetDeviceIdUseCaseImpl(
    private val repository: AppPreferencesRepository,
    private val dispatcher: CoroutineDispatcher
) : SetDeviceIdUseCase {

    override suspend fun invoke(deviceId: Long) {
        catching {
            withContext(dispatcher) {
                repository.setDeviceId(deviceId)
            }
        }
    }
}
