package com.compose.kmplibs.data.datasources.database

import android.util.Log
import com.compose.kmplibs.data.datasources.database.entity.toEntity
import com.compose.kmplibs.data.datasources.database.entity.toModel
import com.compose.kmplibs.data.entity.Movie
import com.compose.kmplibs.data.entity.MovieDetail
import com.compose.kmplibs.data.remote.Result
import com.compose.kmplibs.data.remote.tryCall
import com.compose.kmplibs.data.source.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class LocalDataSourceImpl(
    private val database: AppDatabase,
    private val dispatcher: CoroutineDispatcher // el dispatcher que hemos indicado con koin, el directamente lo inyecta
) : LocalDataSource {

    override suspend fun isEmpty(): Boolean = withContext(dispatcher) {
        Log.d("LocalDataSourceImpl", "->  trycall -> isEmpty")
        database.movieDao().getAllMovies().isEmpty()
    }

    override suspend fun getTopRatedMovies(): Result<List<Movie>> = withContext(dispatcher) {
        tryCall {
            Log.d("LocalDataSourceImpl", "->  trycall -> getTopRatedMovies")
            val movies = database.movieDao().getAllMovies()
            movies.map { it.toModel() }
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetail> = withContext(dispatcher) {
        tryCall {
            Log.d("LocalDataSourceImpl", "->  trycall -> getMovieDetails")
            val movieDetail = database.movieDetailDao().getMovieDetail(id)
            movieDetail?.toModel() ?: throw Exception("MovieDetail not found")
        }
    }

    override suspend fun saveTopRatedMovies(movies: List<Movie>) {
        Log.d("LocalDataSourceImpl", "->  trycall -> saveTopRatedMovies")
        database.movieDao().insertMovies(movies.map { it.toEntity() })
    }

    override suspend fun saveMovieDetail(movieDetail: MovieDetail) {
        Log.d("LocalDataSourceImpl", "->  trycall -> saveMovieDetail")
        database.movieDetailDao().insertMovieDetail(movieDetail.toEntity())
    }
}