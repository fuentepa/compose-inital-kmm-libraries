package com.compose.kmplibs.data.datasources.features.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.compose.kmplibs.data.remote.tryCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class AppPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val dispatcher: CoroutineDispatcher // el dispatcher que hemos indicado con koin, el directamente lo inyecta
) : AppPreferencesRepository {

    companion object {
        private val DEVICE_ID_KEY = longPreferencesKey("DeviceId")
        private val IN_DARK_MODE = booleanPreferencesKey("InDarkMode")
    }

    override fun getDeviceId(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[DEVICE_ID_KEY] ?: 0
    }

    override suspend fun setDeviceId(deviceId: Long): Unit = withContext(dispatcher){
        tryCall {
            dataStore.edit { preferences ->
                preferences[DEVICE_ID_KEY] = deviceId
            }
        }
    }

    override fun isDarkTheme(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IN_DARK_MODE] ?: false
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean): Unit = withContext(dispatcher){
        tryCall {
            dataStore.edit { preferences ->
                preferences[IN_DARK_MODE] = isDarkTheme
            }
        }
    }
}