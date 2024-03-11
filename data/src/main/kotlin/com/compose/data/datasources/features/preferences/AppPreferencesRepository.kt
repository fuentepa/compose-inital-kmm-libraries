package com.compose.data.datasources.features.preferences

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    fun getDeviceId(): Flow<Long>
    suspend fun setDeviceId(deviceId: Long)
}
