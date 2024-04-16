package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.repository.MoviesRepository

fun interface GetMovieDetailUseCase {
    suspend operator fun invoke(id: Int): Result<MovieDetail>
}

class GetMovieDetailUseCaseImpl(private val repository: MoviesRepository) : GetMovieDetailUseCase {
    override suspend operator fun invoke(id: Int): Result<MovieDetail> = repository.getMovieDetail(id)
}