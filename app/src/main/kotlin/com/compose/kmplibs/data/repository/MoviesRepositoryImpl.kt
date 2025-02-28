package com.compose.kmplibs.data.repository

import android.util.Log
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.entity.map
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.remote.TMDBApiService
import com.compose.kmplibs.data.remote.tryCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class MoviesRepositoryImpl(
    private val apiService: TMDBApiService,
    private val dispatcher: CoroutineDispatcher // el dispatcher que hemos indicado con koin, el directamente lo inyecta
) : MoviesRepository {

    override suspend fun getTopRatedMovies(): Result<List<Movie>> = withContext(dispatcher) {
        tryCall {
            Log.d("MoviesRepositoryImpl", "->  trycall -> getTopRatedMovies")
            apiService.getTopRatedMovies().results.map { it.map() }
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetail> = withContext(dispatcher) {
        tryCall {
            Log.d("MoviesRepositoryImpl", "->  trycall -> getMovieDetail")
            apiService.getMovieDetail(id).map()
        }
    }
}