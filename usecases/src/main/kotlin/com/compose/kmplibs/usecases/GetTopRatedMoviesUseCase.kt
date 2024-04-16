package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.repository.MoviesRepository
import com.compose.kmplibs.data.remote.Result

fun interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): Result<List<Movie>>
}

class GetTopRatedMoviesUseCaseImpl(private val repository: MoviesRepository) : GetTopRatedMoviesUseCase {
    override suspend operator fun invoke(): Result<List<Movie>> = repository.getTopRatedMovies()
}