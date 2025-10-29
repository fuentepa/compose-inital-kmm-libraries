package com.compose.kmplibs.data.repository

import com.compose.kmplibs.data.datasources.database.entity.toEntity
import com.compose.kmplibs.data.model.Movie
import com.compose.kmplibs.data.model.MovieDetail
import com.compose.kmplibs.data.remote.response.MovieDetailResponse
import com.compose.kmplibs.data.remote.response.MovieResponse
import com.compose.kmplibs.data.remote.response.toModel
import com.compose.kmplibs.data.source.LocalDataSource
import com.compose.kmplibs.data.source.RemoteDataSource
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
class MoviesRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MoviesRepository {

    override suspend fun getTopRatedMovies(): Result<List<Movie>> {
        if (localDataSource.isEmpty()) {
            val remoteResult: Result<List<MovieResponse>> = remoteDataSource.getTopRatedMovies()

            remoteResult.fold(
                { movies ->
                    localDataSource.saveTopRatedMovies(movies.map { it.toModel() })
                },
                { e ->
                    return Result.failure(e)
                }
            )
        }

        return localDataSource.getTopRatedMovies()
    }

    override suspend fun getMovieDetail(id: Int): Result<MovieDetail> {
        return localDataSource.getMovieDetails(id).fold(
            onSuccess = { Result.success(it) },
            onFailure = {
                remoteDataSource.getMovieDetails(id).fold(
                    onSuccess = { movieDetailResponse ->
                        val movieDetail = movieDetailResponse.toModel()
                        localDataSource.saveMovieDetail(movieDetail)
                        Result.success(movieDetail)
                    },
                    onFailure = { e ->
                        Result.failure(e)
                    }
                )
            }
        )
    }
}