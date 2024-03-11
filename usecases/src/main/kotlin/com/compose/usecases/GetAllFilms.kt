package com.compose.usecases

import com.compose.data.repository.StarWarsRepository

class GetAllFilms(val repository: StarWarsRepository) {

    suspend fun invoke() = repository.getAllfilms()
}