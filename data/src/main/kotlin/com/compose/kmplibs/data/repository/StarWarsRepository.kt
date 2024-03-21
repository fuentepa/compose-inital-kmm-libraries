package com.compose.kmplibs.data.repository

import com.compose.kmplibs.data.entity.Film
import com.compose.kmplibs.data.remote.Result

interface StarWarsRepository {

    suspend fun getAllfilms(): Result<List<Film>>

}