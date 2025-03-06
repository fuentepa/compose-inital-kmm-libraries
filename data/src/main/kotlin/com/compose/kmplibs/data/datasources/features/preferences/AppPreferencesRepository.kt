package com.compose.kmplibs.data.datasources.features.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    fun getDeviceId(): Flow<Long>
    suspend fun setDeviceId(deviceId: Long)
    fun isDarkTheme(): Flow<Boolean>
    suspend fun setDarkTheme(isDarkTheme: Boolean)
}
