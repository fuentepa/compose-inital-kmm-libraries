package com.compose.kmplibs.usecases

import com.compose.kmplibs.data.model.MovieDetail
import com.compose.kmplibs.data.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

fun interface GetMovieDetailUseCase {
    suspend operator fun invoke(id: Int): Result<MovieDetail>
}

@Factory
class GetMovieDetailUseCaseImpl(
    @Provided private val repository: MoviesRepository,
    private val dispatcher: CoroutineDispatcher // el dispatcher que hemos indicado con koin, el directamente lo inyecta
) : GetMovieDetailUseCase {
    override suspend operator fun invoke(id: Int): Result<MovieDetail> = withContext(dispatcher) {
        repository.getMovieDetail(id)
    }
}
