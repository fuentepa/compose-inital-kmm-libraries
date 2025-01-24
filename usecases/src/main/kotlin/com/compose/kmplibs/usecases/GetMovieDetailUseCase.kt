package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.repository.MoviesRepository
import org.koin.core.annotation.Factory

fun interface GetMovieDetailUseCase {
    suspend operator fun invoke(id: Int): Result<MovieDetail>
}

@Factory
class GetMovieDetailUseCaseImpl(private val repository: MoviesRepository) : GetMovieDetailUseCase {
    override suspend operator fun invoke(id: Int): Result<MovieDetail> = repository.getMovieDetails(id)
}