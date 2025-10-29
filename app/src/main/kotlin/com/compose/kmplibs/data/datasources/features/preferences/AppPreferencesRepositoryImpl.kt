package com.compose.kmplibs.data.datasources.features.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.compose.kmplibs.data.utils.suspendCatching
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class AppPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferencesRepository {

    companion object {
        private val DEVICE_ID_KEY = longPreferencesKey("DeviceId")
        private val IN_DARK_MODE = booleanPreferencesKey("InDarkMode")
    }

    override fun getDeviceId(): Flow<Long> = dataStore.data.map { preferences ->
        preferences[DEVICE_ID_KEY] ?: 0
    }

    override suspend fun setDeviceId(deviceId: Long) {
        suspendCatching {
            dataStore.edit { preferences ->
                preferences[DEVICE_ID_KEY] = deviceId
            }
        }
    }

    override fun isDarkTheme(): Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IN_DARK_MODE] ?: false
    }

    override suspend fun setDarkTheme(isDarkTheme: Boolean) {
        suspendCatching {
            dataStore.edit { preferences ->
                preferences[IN_DARK_MODE] = isDarkTheme
            }
        }
    }
}