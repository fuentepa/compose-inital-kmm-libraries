package com.compose.data.repository

import com.compose.data.entity.Film
import com.compose.data.remote.Result

interface StarWarsRepository {

    suspend fun getAllfilms(): Result<List<Film>>

}