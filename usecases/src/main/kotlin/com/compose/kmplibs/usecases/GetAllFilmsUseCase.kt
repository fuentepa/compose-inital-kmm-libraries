package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.entity.Film
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.repository.StarWarsRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single


interface GetAllFilmsUseCase {
    suspend operator fun invoke(): Result<List<Film>>
}

class GetAllFilmsUseCaseImpl(private val repository: StarWarsRepository) : GetAllFilmsUseCase {
    override suspend operator fun invoke(): Result<List<Film>> = repository.getAllfilms()
}