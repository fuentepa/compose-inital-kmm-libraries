package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

fun interface GetTopRatedMoviesUseCase {
    suspend operator fun invoke(): Result<List<Movie>>
}

@Factory
class GetTopRatedMoviesUseCaseImpl(
    private val repository: MoviesRepository,
    private val dispatcher: CoroutineDispatcher // el dispatcher que hemos indicado con koin, el directamente lo inyecta
) : GetTopRatedMoviesUseCase {
    override suspend operator fun invoke(): Result<List<Movie>> = withContext(dispatcher) {
        repository.getTopRatedMovies()
    }
}