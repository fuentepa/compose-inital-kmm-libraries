package com.compose.kmplibs.data.repository

import com.compose.data.entity.Film
import com.compose.data.entity.map
import com.compose.data.remote.Result
import com.compose.data.remote.StarWarsApiService
import com.compose.data.remote.tryCall
import com.compose.data.repository.StarWarsRepository

class StarWarsRepositoryImpl(private val starWarsApiService: StarWarsApiService) : StarWarsRepository {

    override suspend fun getAllfilms(): Result<List<Film>> = tryCall {
        starWarsApiService.getAllfilms().map { it.map() }
    }

}