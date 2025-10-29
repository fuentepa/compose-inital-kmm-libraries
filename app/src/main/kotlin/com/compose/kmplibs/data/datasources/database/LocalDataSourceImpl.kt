package com.compose.kmplibs.data.datasources.database

import com.compose.kmplibs.data.datasources.database.entity.toEntity
import com.compose.kmplibs.data.datasources.database.entity.toModel
import com.compose.kmplibs.data.datasources.database.error.LocalException
import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.model.MovieDetail
import com.compose.kmplibs.data.source.LocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
class LocalDataSourceImpl(
    private val database: AppDatabase
) : LocalDataSource {

    override suspend fun isEmpty(): Boolean = database.movieDao().getMoviesCount() == 0

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        return try {
            val movies = database.movieDao().getTopRatedMovies()
                .map { movieEntities -> movieEntities.map { it.toModel() } }
                .firstOrNull()
            Result.success(movies ?: emptyList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetail> {
        val movieDetail = database.movieDetailDao().getMovieDetail(id)
        return movieDetail?.let {
             Result.success( it.toModel())
        } ?: Result.failure(LocalException.LocalStorage("Movie detail with id=$id not found" ))
    }

    override suspend fun saveTopRatedMovies(movies: List<Movie>) {
        database.movieDao().insertMovies(movies.map { it.toEntity() })
    }

    override suspend fun saveMovieDetail(movieDetail: MovieDetail) {
        database.movieDetailDao().insertMovieDetail(movieDetail.toEntity())
    }
}