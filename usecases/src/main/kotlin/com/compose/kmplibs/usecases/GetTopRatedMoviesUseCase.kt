package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.repository.MoviesRepository
import com.compose.kmplibs.data.remote.Result
import org.koin.core.annotation.Factory

fun interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): Result<List<Movie>>
}

@Factory
class GetTopRatedMoviesUseCaseImpl(private val repository: MoviesRepository) : GetTopRatedMoviesUseCase {
    override suspend operator fun invoke(): Result<List<Movie>> = repository.getTopRatedMovies()
}