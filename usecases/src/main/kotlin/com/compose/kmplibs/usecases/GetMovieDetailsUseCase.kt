package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.repository.MoviesRepository

fun interface GetMovieDetailsUseCase {
    suspend operator fun invoke(id: Int): Result<Movie>
}

class GetMovieDetailsUseCaseImpl(private val repository: MoviesRepository) : GetMovieDetailsUseCase {
    override suspend operator fun invoke(id: Int): Result<Movie> = repository.getMovieDetail(id)
}